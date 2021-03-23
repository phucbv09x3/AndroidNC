package com.example.androidnc.ui.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.androidnc.BR
import com.example.bitcoin.data.scheduler.ISchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import com.example.androidnc.data.DataManager
import com.example.bitcoin.data.local.service.CoinService
import com.example.androidnc.utils.printLog
import javax.inject.Inject
import kotlin.reflect.KClass


/**
 * Created by Phuc on 15/1/2021
 */
abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : Fragment(),
    IFragmentCallback {
    var TAG = "BaseFragment"

    @Inject
    lateinit var dataManager: DataManager

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var schedule: ISchedulerProvider
    private val disposable = CompositeDisposable()

    lateinit var activity: Activity
    lateinit var navigators: Navigators
    lateinit var viewModel: VM
    lateinit var dataBinding: DB

    @Inject
    lateinit var service:CoinService
    fun inject(
        factory: ViewModelFactory,
        dataManager: DataManager,
        schedulerProvider: ISchedulerProvider,
        service: CoinService
    ) {
        this.factory = factory
        this.dataManager = dataManager
        this.schedule = schedulerProvider
        this.service=service
    }


    private val mProgressDialog: ProgressDialog by lazy {
        ProgressDialog(activity)
    }
    private var mIsAttached: Boolean = false

    override fun getContext(): Context {
        return activity
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        TAG = javaClass.simpleName
        printLog("onAttach")
        mIsAttached = true
        if (context is Navigators) {
            context.fragmentRequestInject(this)
            activity = context as Activity
            navigators = context
            viewModel = ViewModelProvider(this, factory).get(createViewModel())
            viewModel.fragmentCallback = this
            viewModel.navigation = navigators
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        printLog("onCreateView")
        return onCreateViewInternal(inflater, container)
    }

    var isFirst = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        printLog("onViewCreated")
        setupUI(view)
        navigators.onFragmentResumed(this)
        initView()
        bindViewModel()
        trackingProgress()
    }

    private fun setupUI(view: View) {
        if (view !is EditText) {
            view.setOnTouchListener { _, _ ->
                hideSoftKeyboard()
                false
            }
        }
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupUI(innerView)
            }
        }
    }

    private fun hideSoftKeyboard() {
        activity.currentFocus?.let {
            val inputMethodManager =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun trackingProgress() {
//        viewModel.progressBar
//            .subscribeOn(schedule.io)
//            .observeOn(schedule.ui)
//            .subscribe {
//                printLog("trackingProgress $it")
//                if (it) {
//                    showLoading()
//                } else {
//                    hideLoading()
//                }
//            }.addDisposable()
    }

    private fun onCreateViewInternal(inflater: LayoutInflater, container: ViewGroup?): View? {
        var rootView: View? = null
        val resId = getResourceLayout()
        if (resId > 0) {
            dataBinding = DataBindingUtil.inflate(
                inflater,
                getResourceLayout(),
                container,
                false
            )
            dataBinding.setVariable(BR.viewModel, viewModel)
            rootView = dataBinding.root
        }

        return rootView
    }

    abstract fun createViewModel(): Class<VM>
    abstract fun getResourceLayout(): Int
    abstract fun initView()
    abstract fun bindViewModel()
    open fun isShowToolbar(): Boolean {
        return true
    }

    open fun isShowBack(): Boolean {
        return true
    }

    override fun showLoading() {
        if (!mProgressDialog.isShowing && userVisibleHint) {
            mProgressDialog.show()
        }

    }

    override fun hideLoading() {
        if (mProgressDialog.isShowing && !isDetached) {
            mProgressDialog.dismiss()
        }
    }

    open fun showActionBar(): Boolean {
        return true
    }


    fun replaceFragment(
        fragmentId: KClass<*>,
        bundle: Bundle? = null,
        addToBackStack: Boolean = true
    ) {
        navigators.switchFragment(fragmentId, bundle, addToBackStack)
    }


    class ProgressDialog constructor(context: Context) : Dialog(context) {
        init {
            setCancelable(false)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            //setContentView(R.layout.progress_circle)
            val window = window
            if (window != null) {
                getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)
            }
        }
    }

    fun Disposable.addDisposable() {
        disposable.add(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.onDestroyView()
        disposable.clear()

    }

    override fun onDetach() {
        super.onDetach()
        mIsAttached = false
    }
}