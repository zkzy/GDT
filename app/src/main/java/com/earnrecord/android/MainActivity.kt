package com.earnrecord.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gdt.GDTManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GDTManager.loadNativeExpressAD(this,view_group,"1109881164","6000486514044427")
    }
}
