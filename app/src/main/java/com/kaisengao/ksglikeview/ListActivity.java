package com.kaisengao.ksglikeview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @ClassName: ListActivity
 * @Author: KaiSenGao
 * @CreateDate: 8/5/21 11:06 PM
 * @Description: 列表模式
 */
public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_list);
    }
}