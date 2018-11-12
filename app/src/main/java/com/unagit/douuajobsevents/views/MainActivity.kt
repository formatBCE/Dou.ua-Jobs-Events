package com.unagit.douuajobsevents.views

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.unagit.douuajobsevents.R
import com.unagit.douuajobsevents.contracts.ListContract
import com.unagit.douuajobsevents.models.AppDatabase
import com.unagit.douuajobsevents.models.DataInjector
import com.unagit.douuajobsevents.models.DataProvider
import com.unagit.douuajobsevents.models.Item
import com.unagit.douuajobsevents.presenters.ListPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item.*
import android.util.Pair as AndroidPair

class MainActivity : AppCompatActivity(), ListContract.ListView {

    private val presenter: ListContract.ListPresenter = ListPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.attach(this, application)
        presenter.getItems()

        val listener = object : ItemAdapter.Listener {
            override fun onItemClicked(position: Int) {
//                Snackbar.make(activityMainLayout, item.guid, Snackbar.LENGTH_SHORT)
//                        .show()
                presenter.itemClicked(position)
            }
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = ItemAdapter(null, listener)
        }

        button.setOnClickListener {
            presenter.refreshData()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    override fun showLoading(show: Boolean) {
        if (show) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }


    override fun showItems(items: List<Item>) {
        val adapter = recyclerView.adapter as ItemAdapter
        adapter.setData(items.toMutableList())
    }

    override fun insertNewItems(newItems: List<Item>) {
        val adapter = recyclerView.adapter as ItemAdapter
        val insertPosition = 0
        adapter.insertData(newItems, insertPosition)
        recyclerView.scrollToPosition(insertPosition)
    }


    override fun showItemDetails(position: Int, guid: String) {
//        val itemView =
// recyclerView.layoutManager?.getChildAt(position)
//        val imgView = itemView?.findViewById<View>(R.id.itemImg)
//        val titleView = itemView?.findViewById<View>(R.id.itemTitle)
//
        val detailsIntent = Intent(this@MainActivity, DetailsActivity::class.java)
        detailsIntent.putExtra(getString(R.string.extra_guid_id), guid)
//        val transImgName = getString(R.string.transition_img_name)
//        val transTitleName = getString(R.string.transition_title_name)
//        val transContainerName = getString(R.string.transition_container_name)
//        val transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(
//                this@MainActivity,
//
//                AndroidPair.create(imgView, transImgName),
//                AndroidPair.create(titleView, transTitleName),
//                AndroidPair.create(itemView, transContainerName)
//        )
//
//
//
        startActivity(detailsIntent) //, transitionActivityOptions.toBundle())

    }

    override fun showSnackbar(string: String) {
        Snackbar.make(activityMainLayout, string, Snackbar.LENGTH_SHORT).show()
    }

    override fun hasNetwork(): Boolean {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return (activeNetwork != null && activeNetwork.isConnected)
    }
}
