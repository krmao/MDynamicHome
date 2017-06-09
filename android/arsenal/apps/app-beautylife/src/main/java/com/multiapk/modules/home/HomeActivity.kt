package com.multiapk.modules.home

import android.Manifest
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.util.Log
import com.jakewharton.rxbinding2.view.RxView
import com.jude.swipbackhelper.SwipeBackHelper
import com.multiapk.R
import com.multiapk.base.DefaultApplication
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import org.smartrobot.base.DefaultActivity
import org.smartrobot.base.DefaultBaseActivity
import org.smartrobot.database.model.Order
import org.smartrobot.util.rx.RxBus
import org.smartrobot.util.rx.RxTestEvent
import java.util.concurrent.TimeUnit

class HomeActivity : DefaultBaseActivity() {
    private val subscriptions: CompositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.inflateMenu(R.menu.home_menu)
        toolbar.setOnMenuItemClickListener {
            Snackbar.make(toolbar, "您点击了:" + it?.title, Snackbar.LENGTH_SHORT).show()
            if (it.itemId == R.id.action_order) {
                DefaultActivity.start(this, "com.multiapk.modules.order.OrderFragment")
            }
            true
        }
        val searchItem = toolbar.menu.findItem(R.id.action_search)
        val searchView: SearchView? = searchItem?.actionView as SearchView

        searchView?.setSearchableInfo((getSystemService(Context.SEARCH_SERVICE) as SearchManager).getSearchableInfo(componentName))
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.i("krmao", "onQueryTextSubmit:" + query)
                Snackbar.make(searchView, "您提交了:" + query, Snackbar.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.w("krmao", "onQueryTextChange:" + newText)
                Snackbar.make(searchView, "文字更改:" + newText, Snackbar.LENGTH_SHORT).show()
                return true
            }
        })

        cardViewComputerModule.setOnClickListener {
            longToast("电脑模块")
            DefaultActivity.start(this, "com.multiapk.modules.computer.ComputerFragment")
        }
        cardViewMobileModule.setOnClickListener {
            toast("手机模块")
            DefaultActivity.start(this, "com.multiapk.modules.mobile.MobileFragment")
        }

        RxView.clicks(button).debounce(200, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe {
            Snackbar.make(button, "hello michael", Snackbar.LENGTH_SHORT).setAction("undo", {
                toast("you clicked undo")
            }).setActionTextColor(R.color.material_blue_grey_800).show()
            RxBus.instance.post(RxTestEvent("点击首页按钮"))
        }

        val rxPermissions = RxPermissions(this)
        rxPermissions.request(Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS).subscribe { granted ->
            if (granted!!) {
                // All permissions were granted//
                Log.d("krmao", "All permissions were granted")
            } else {
                //One or more permissions was denied//
                Log.d("krmao", "One or more permissions was denied")
            }
        }
        rxPermissions.request(Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS).subscribe({ granted ->
            if (granted!!) {
                // All permissions were granted//
                Log.d("krmao", "All permissions were granted")
            } else {
                //One or more permissions was denied//
                Log.d("krmao", "One or more permissions was denied")
            }

        })
        rxPermissions.setLogging(true)
        rxPermissions.requestEach(Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE).subscribe({ permission ->
            if (permission.granted) {
                // `permission.name` is granted !
                Log.d("krmao", "permission:" + permission.name + " is granted")
            } else if (permission.shouldShowRequestPermissionRationale) {
                // Denied permission without ask never again
                Log.d("krmao", "Denied permission without ask never again")
                //rxPermissions.shouldShowRequestPermissionRationale(HomeActivity.this, Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE);
            } else {
                // Denied permission with ask never again
                // Need to go to the settings
                Log.d("krmao", "Need to go to the settings")
            }
        })

        testDB()

        subscriptions.add(RxBus.instance.toObservable(RxTestEvent::class.java).subscribe { event ->
            Snackbar.make(button, event.content + ":thread=" + Thread.currentThread().name, Snackbar.LENGTH_SHORT).show()
            collapsingToolbarLayout.title = event.content
        })
    }

    fun testDB() {
        val daoSession = (application as DefaultApplication).getDaoSession()
        val orderDao = daoSession.orderDao

        orderDao?.insert(Order())
        orderDao?.insert(Order())
        orderDao?.insert(Order())
        orderDao?.insert(Order())
        orderDao?.insert(Order())
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.dispose()
    }
}