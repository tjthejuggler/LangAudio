package com.example.langaudio

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.langaudio.R
import java.util.concurrent.TimeUnit
import android.content.Intent

import android.content.BroadcastReceiver
import android.content.Context
import android.os.Parcelable
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.widget.*
import androidx.core.view.MotionEventCompat

//todo make background dark
//todo hook up tracks to dropdown
//todo a way to add more mp3s from phone
//todo hook up voice commands
//make different swipes(voice commands)
//  pause swipe
//  longer rewind swipe?

class MainActivity : AppCompatActivity() {




    private var forwardbtn: ImageButton? = null
    private var backwardbtn: ImageButton? = null
    private var pausebtn: ImageButton? = null
    private var playbtn: ImageButton? = null
    private var mPlayer1: MediaPlayer? = null
    private var mPlayer2: MediaPlayer? = null
    private var songName: TextView? = null
    private var startTime: TextView? = null
    private var songTime: TextView? = null
    private var songPrgs: SeekBar? = null
    private val hdlr = Handler()

    override fun onTouchEvent(event: MotionEvent): Boolean {

        val action: Int = MotionEventCompat.getActionMasked(event)

        return when (action) {
            MotionEvent.ACTION_UP -> {
                if(mPlayer1!!.isPlaying())
                {
                    mPlayer1!!.pause()
                    mPlayer2!!.seekTo(sTime - 8000)
                    mPlayer2!!.start()
                }
                else if(mPlayer2!!.isPlaying())
                {
                    mPlayer2!!.pause()
                    mPlayer1!!.seekTo(sTime - 8000)
                    mPlayer1!!.start()
                }

                Log.d("ChessApp", "Action was UP")
                true
            }
            else -> super.onTouchEvent(event)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        backwardbtn = findViewById<View>(R.id.btnBackward) as ImageButton
        forwardbtn = findViewById<View>(R.id.btnForward) as ImageButton
        playbtn = findViewById<View>(R.id.btnPlay) as ImageButton
        pausebtn = findViewById<View>(R.id.btnPause) as ImageButton

        startTime = findViewById<View>(R.id.txtStartTime) as TextView
        songTime = findViewById<View>(R.id.txtSongTime) as TextView

        mPlayer1 = MediaPlayer.create(this, R.raw.lionmouseen)
        mPlayer2 = MediaPlayer.create(this, R.raw.lionmousetr)
        songPrgs = findViewById<View>(R.id.sBar) as SeekBar
        songPrgs!!.isClickable = false
        pausebtn!!.isEnabled = false

        val turkishstorylist = R.array.turkishStories
        val spanishstorylist =  R.array.spanishStories
        val germanstorylist =  R.array.germanStories

        var currentstory = resources.getStringArray(turkishstorylist)
        val languagelist = R.array.languages
        // access the spinner
        val storyspinner = findViewById<Spinner>(R.id.storyspinner)

        val turkishstoryadapter = ArrayAdapter.createFromResource(this,
            turkishstorylist, R.layout.spinner_item, )
        val spanishstoryadapter = ArrayAdapter.createFromResource(this,
            spanishstorylist, R.layout.spinner_item, )
        val germanstoryadapter = ArrayAdapter.createFromResource(this,
            germanstorylist, R.layout.spinner_item, )

        fun setPlayersLionMouse(first: Int, second: Int ){
            mPlayer1!!.pause(); mPlayer1!!.stop(); mPlayer1!!.release(); mPlayer1 = MediaPlayer.create(this, first);
            mPlayer2!!.pause(); mPlayer2!!.stop(); mPlayer2!!.release(); mPlayer2 = MediaPlayer.create(this, second);
            sTime = 0
            pausebtn!!.isEnabled = false
            playbtn!!.isEnabled = true
            songPrgs!!.progress = sTime
        }
//        fun setPlayersLionMouse2(){
//            mPlayer1!!.stop(); mPlayer1!!.release(); mPlayer1 = MediaPlayer.create(this, R.raw.lionmouseen);
//            mPlayer2!!.stop(); mPlayer2!!.release(); mPlayer2 = MediaPlayer.create(this, R.raw.lionmousetr);
//            sTime = 0
//        }
        if (storyspinner != null) {

            storyspinner.adapter = turkishstoryadapter
            storyspinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View?, position: Int, id: Long) {

                    when(currentstory[position]) {
                        "Aslan ve Fare" -> setPlayersLionMouse(R.raw.lionmouseen, R.raw.lionmousetr)
                        "Çirkin Ördek Yavrusu" -> setPlayersLionMouse(R.raw.uglyducken, R.raw.uglyducktr)
                        "Güzel Ve Çirkin" -> setPlayersLionMouse(R.raw.beautybeasten, R.raw.beautybeasttr)
                        "Heidi" -> setPlayersLionMouse(R.raw.heidien, R.raw.heiditr)
                        "Jack ve Fasulye Sırığı" -> setPlayersLionMouse(R.raw.jackbeanen, R.raw.jackbeantr)
                        "Küçük kızıl tavuk" -> setPlayersLionMouse(R.raw.littleredhenen, R.raw.littleredhentr)
                        "Şehir faresi ve köy faresi" -> setPlayersLionMouse(R.raw.townmouseen, R.raw.townmousetr)
                        "Zencefilli kurabiye adam" -> setPlayersLionMouse(R.raw.gingerbreaden, R.raw.gingerbreadtr)

                        "El león y el ratón" -> setPlayersLionMouse(R.raw.lionmouseen, R.raw.lionmousees)
                        "El patito feo" -> setPlayersLionMouse(R.raw.uglyducken, R.raw.uglyduckes)
                        "La Bella Y La Bestia" -> setPlayersLionMouse(R.raw.beautybeasten, R.raw.beautybeastes)
                        "Jack y las Habichuelas Mágicas" -> setPlayersLionMouse(R.raw.jackbeanen, R.raw.jackbeanes)
                        "El Raton de Campo y Raton de Ciudad" -> setPlayersLionMouse(R.raw.townmouseen, R.raw.townmousees)
                        "El hombre de jengibre" -> setPlayersLionMouse(R.raw.gingerbreaden, R.raw.gingerbreades)

                        "Der Löwe und die Maus" -> setPlayersLionMouse(R.raw.lionmouseen, R.raw.lionmousede)
                        "Das hässliche Entlein" -> setPlayersLionMouse(R.raw.uglyducken, R.raw.uglyduckes)
                        "Jack und die Bohnenstange" -> setPlayersLionMouse(R.raw.jackbeanen, R.raw.jackbeande)
                        "Der Lebkuchenmann" -> setPlayersLionMouse(R.raw.gingerbreaden, R.raw.gingerbreadde)
                    }

                    Toast.makeText(this@MainActivity,
                        "Story: " +
                                "" + currentstory[position], Toast.LENGTH_SHORT).show()
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
        val languagespinner = findViewById<Spinner>(R.id.languagespinner)
        if (languagespinner != null) {
            val languageadapter = ArrayAdapter.createFromResource(this,
                languagelist, R.layout.spinner_item, )
            languagespinner.adapter = languageadapter

            languagespinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View?, position: Int, id: Long) {
                    if (resources.getStringArray(languagelist)[position]=="Español"){
                        currentstory = resources.getStringArray(spanishstorylist)
                        storyspinner.adapter = spanishstoryadapter
                    }
                    else if (resources.getStringArray(languagelist)[position]=="Türkçe"){
                        currentstory = resources.getStringArray(turkishstorylist)
                        storyspinner.adapter = turkishstoryadapter
                    }
                    else if (resources.getStringArray(languagelist)[position]=="Deutsch"){
                        currentstory = resources.getStringArray(germanstorylist)
                        storyspinner.adapter = germanstoryadapter
                    }
                    Toast.makeText(this@MainActivity,
                        "Language: " +
                                "" + resources.getStringArray(languagelist)[position], Toast.LENGTH_SHORT).show()
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        playbtn!!.setOnClickListener {
            Toast.makeText(this@MainActivity, "Playing Audio", Toast.LENGTH_SHORT).show()
            mPlayer1!!.start()
            mPlayer2!!.start()
            mPlayer2!!.pause()
            eTime = mPlayer1!!.duration
            sTime = mPlayer1!!.currentPosition
            if (oTime == 0) {
                songPrgs!!.max = eTime
                oTime = 1
            }
            songTime!!.text = String.format(
                "%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(eTime.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(eTime.toLong()) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(eTime.toLong())
                )
            )
            startTime!!.text = String.format(
                "%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(sTime.toLong()),
                TimeUnit.MILLISECONDS.toSeconds(sTime.toLong()) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(sTime.toLong())
                )
            )
            songPrgs!!.progress = sTime
            hdlr.postDelayed(UpdateSongTime, 100)
            pausebtn!!.isEnabled = true
            playbtn!!.isEnabled = false
        }
        pausebtn!!.setOnClickListener {
            mPlayer1!!.pause()
            mPlayer2!!.pause()
            pausebtn!!.isEnabled = false
            playbtn!!.isEnabled = true
            Toast.makeText(applicationContext, "Pausing Audio", Toast.LENGTH_SHORT).show()
        }
        forwardbtn!!.setOnClickListener {
            if (sTime + fTime <= eTime) {
                sTime =
                    sTime + fTime
                mPlayer1!!.seekTo(sTime)
                mPlayer2!!.seekTo(sTime)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Cannot jump forward 5 seconds",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (!playbtn!!.isEnabled) {
                playbtn!!.isEnabled = true
            }
        }
        backwardbtn!!.setOnClickListener {
            if (sTime - bTime > 0) {
                sTime =
                    sTime - bTime
                mPlayer1!!.seekTo(sTime)
                mPlayer2!!.seekTo(sTime)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Cannot jump backward 5 seconds",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (!playbtn!!.isEnabled) {
                playbtn!!.isEnabled = true
            }


        }
    }

    private val UpdateSongTime: Runnable = object : Runnable {
        override fun run() {
            if(mPlayer1!!.isPlaying())
            {
                sTime = mPlayer1!!.currentPosition
            }
            else if(mPlayer2!!.isPlaying())
            {
                sTime = mPlayer2!!.currentPosition
            }

            startTime!!.text = String.format(
                "%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(
                    sTime.toLong()
                ),
                TimeUnit.MILLISECONDS.toSeconds(sTime.toLong()) - TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(
                        sTime.toLong()
                    )
                )
            )
            songPrgs!!.progress = sTime
            hdlr.postDelayed(this, 100)
        }
    }

    companion object {
        private var oTime = 0
        private var sTime = 0
        private var eTime = 0
        private const val fTime = 5000
        private const val bTime = 5000
    }




}



