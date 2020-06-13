package com.hrtzpi.activities

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog
import com.google.android.material.tabs.TabLayout
import com.hrtzpi.R
import com.hrtzpi.baseactivity.BaseActivity
import com.hrtzpi.fragments.AccountFragment
import com.hrtzpi.fragments.FavFragment
import com.hrtzpi.fragments.MainFragment
import com.hrtzpi.fragments.ProductsFragment
import com.hrtzpi.helpers.StaticMembers
import com.hrtzpi.helpers.StaticMembers.CATEGORY_ID
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    private var isBShowing: Boolean = false
    private val CAT = "cat"
    private val PRODUCTS = "products"
    lateinit var params: HashMap<String, String>
    lateinit var productsFragment: ProductsFragment
    lateinit var mainFragment: MainFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        params = HashMap()
        productsFragment = ProductsFragment.getInstance(params)
        openFragment(R.id.containerProducts, productsFragment, PRODUCTS)
        mainFragment = MainFragment.getInstance {
            params[CATEGORY_ID] = it
            switchToProducts()
        }
        openFragment(R.id.containerMain, mainFragment, CAT)
        containerMain.visibility = VISIBLE
        containerProducts.visibility = GONE
        // profile.setOnClickListener { AccountFragment.getInstance().show(supportFragmentManager, getString(R.string.account)) }
        cart.setOnClickListener { startActivity(Intent(baseContext, CartActivity::class.java)) }
        search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                params[StaticMembers.SEARCH] = search.text.toString()
                switchToProducts()
                return@setOnEditorActionListener true
            }
            false
        }
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        switchToMain()
                    }
                    else -> {
                        isBShowing = true
                        containerMain.visibility = GONE
                        containerProducts.visibility = GONE
                        containerOther.visibility = VISIBLE
                        when (tab.position) {
                            1 -> {
                                val par = HashMap<String,String>()
                                par.put(StaticMembers.VIDEO,"1")
                                val productsVideoFragment = ProductsFragment.getInstance(par)
                                openFragment(R.id.containerOther, productsVideoFragment, "vid")
                            }
                            2 -> {
                                openFragment(R.id.containerOther, FavFragment(), "fav")
                            }
                            3 -> {
                                openFragment(R.id.containerOther, AccountFragment(), "account")
                            }
                        }
                    }
                }
            }
        })
    }

    override fun onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return
        }
        if (isBShowing)
            switchToMain()
        else
            MaterialStyledDialog.Builder(this)
                    .setDescription(R.string.want_to_exit)
                    .setPositiveText(R.string.yes)
                    .setNegativeText(R.string.no)
                    .setIcon(R.mipmap.ic_launcher)
                    .setHeaderColor(R.color.colorPrimary)
                    .withDialogAnimation(true)
                    .withIconAnimation(true)
                    .onPositive { dialog, which ->
                        dialog.dismiss()
                        android.os.Process.killProcess(android.os.Process.myPid())
                        System.exit(1)
                        finish()

                    }.onNegative { dialog, which -> dialog.dismiss() }
                    .show()
    }

    private fun switchToMain() {
        isBShowing = false
        containerMain.visibility = VISIBLE
        containerOther.visibility = GONE
        containerProducts.visibility = GONE
        params.remove(CATEGORY_ID)
        productsFragment.removeProducts()
        tabLayout.getTabAt(0)?.select();

    }

    private fun switchToProducts() {
        isBShowing = true
        containerMain.visibility = GONE
        containerOther.visibility = GONE
        containerProducts.visibility = VISIBLE
        productsFragment.getProducts()
    }

    private fun openFragment(id: Int, fragment: Fragment, tag: String) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(id, fragment, tag)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onPause() {
        super.onPause()
        JCVideoPlayer.releaseAllVideos()
    }


}
