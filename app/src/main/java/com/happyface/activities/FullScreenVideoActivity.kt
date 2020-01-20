package com.happyface.activities

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.happyface.R
import com.happyface.baseactivity.BaseActivity
import com.happyface.helpers.StaticMembers
import com.happyface.models.video_models.DataItem
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard
import kotlinx.android.synthetic.main.activity_full_screen_video.*


class FullScreenVideoActivity : BaseActivity() {
    private lateinit var video: DataItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        window.statusBarColor = resources.getColor(R.color.black)
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        setContentView(R.layout.activity_full_screen_video)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        video = intent.getSerializableExtra(StaticMembers.VIDEO) as DataItem
        videoPlayer.setUp(video.video, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, video.name)
        videoPlayer.fullscreenButton.visibility = GONE
        videoPlayer.backButton.visibility = VISIBLE
        videoPlayer.backButton.setOnClickListener {
            JCVideoPlayerStandard.releaseAllVideos()
            onBackPressed()
        }
    }

    override fun onPause() {
        super.onPause()
        videoPlayer.onStatePause()
    }

    override fun onBackPressed() {
        finish()
    }
}
