package com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.bumptech.glide.RequestManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.R
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.models.AUTH_TOKEN_BUNDLE_KEY
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.models.AuthToken
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.BaseActivity
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.auth.AuthActivity
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.main.account.BaseAccountFragment
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.main.account.ChangePasswordFragment
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.main.account.UpdateAccountFragment
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.main.blog.BaseBlogFragment
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.main.blog.UpdateBlogFragment
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.main.blog.ViewBlogFragment
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.ui.main.create_blog.BaseCreateBlogFragment
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.util.BottomNavController
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.util.setUpNavigation
import com.mfcwl.library.powerfulandroidapps_processdeathissuefix.viewmodels.ViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.progress_bar
import javax.inject.Inject

class MainActivity : BaseActivity(),
    BottomNavController.NavGraphProvider,
    BottomNavController.OnNavigationGraphChanged,
    BottomNavController.OnNavigationReselectedListener,
    MainDependencyProvider {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var requestManager: RequestManager

    override fun getGlideRequestManager(): RequestManager {
        return requestManager
    }

    override fun getVMProviderFactory(): ViewModelProviderFactory {
        return providerFactory
    }

    private lateinit var bottomNavigationView: BottomNavigationView

    private val bottomNavController by lazy(LazyThreadSafetyMode.NONE) {
        BottomNavController(
            this,
            R.id.main_nav_host_fragment,
            R.id.nav_blog,
            this,
            this
        )
    }

    override fun getNavGraphId(itemId: Int) = when (itemId) {
        R.id.nav_blog -> {
            R.navigation.nav_blog
        }
        R.id.nav_create_blog -> {
            R.navigation.nav_create_blog
        }
        R.id.nav_account -> {
            R.navigation.nav_account
        }
        else -> {
            R.navigation.nav_blog
        }
    }

    override fun onGraphChange() {
        cancelActiveJobs()
        expandAppBar()
    }

    private fun cancelActiveJobs() {
        val fragments = bottomNavController.fragmentManager
            .findFragmentById(bottomNavController.containerId)
            ?.childFragmentManager
            ?.fragments
        if (fragments != null) {
            for (fragment in fragments) {
                if (fragment is BaseAccountFragment) {
                    fragment.cancelActiveJobs()
                }
                if (fragment is BaseBlogFragment) {
                    fragment.cancelActiveJobs()
                }
                if (fragment is BaseCreateBlogFragment) {
                    fragment.cancelActiveJobs()
                }
            }
        }
        displayProgressBar(false)
    }

    override fun onReselectNavItem(
        navController: NavController,
        fragment: Fragment
    ) = when (fragment) {

        is ViewBlogFragment -> {
            navController.navigate(R.id.action_viewBlogFragment_to_home)
        }

        is UpdateBlogFragment -> {
            navController.navigate(R.id.action_updateBlogFragment_to_home)
        }

        is UpdateAccountFragment -> {
            navController.navigate(R.id.action_updateAccountFragment_to_home)
        }

        is ChangePasswordFragment -> {
            navController.navigate(R.id.action_changePasswordFragment_to_home)
        }

        else -> {
            // do nothing
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar()
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottomNavigationView.setUpNavigation(bottomNavController, this)
        if (savedInstanceState == null) {
            bottomNavController.onNavigationItemSelected()
        }

        subscribeObservers()
        restoreSession(savedInstanceState)
    }

    private fun restoreSession(savedInstanceState: Bundle?) {
        savedInstanceState?.get(AUTH_TOKEN_BUNDLE_KEY)?.let { authToken ->
            sessionManager.setValue(authToken as AuthToken)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(AUTH_TOKEN_BUNDLE_KEY, sessionManager.cachedToken.value)
    }

    private fun setupActionBar() {
        setSupportActionBar(tool_bar)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() = bottomNavController.onBackPressed()

    fun subscribeObservers() {
        sessionManager.cachedToken.observe(this, Observer { authToken ->
            Log.d(TAG, "MainActivity, subscribeObservers: ViewState: ${authToken}")
            if (authToken == null || authToken.account_pk == -1 || authToken.token == null) {
                navAuthActivity()
                finish()
            }
        })
    }

    private fun navAuthActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun displayProgressBar(bool: Boolean) {
        if (bool) {
            progress_bar.visibility = View.VISIBLE
        } else {
            progress_bar.visibility = View.INVISIBLE
        }
    }

    override fun expandAppBar() {
        findViewById<AppBarLayout>(R.id.app_bar).setExpanded(true)
    }

}