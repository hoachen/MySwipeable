package com.chh.swipeablecard;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.chh.model.CardModel;
import com.lorentzos.flingswipe.MyCardAdapter;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

public class MyActivity extends Activity {
    private ArrayList<CardModel> al;
//    private ArrayAdapter<String> arrayAdapter;
    private MyCardAdapter mAdapter;
    private int i;

    //    @InjectView(R.id.frame)
    SwipeFlingAdapterView flingContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
//        ButterKnife.inject(this);


        al = new ArrayList<CardModel>();
        al.add(new CardModel("chh",21,4));
        al.add(new CardModel("ruansheng",23,6));
        al.add(new CardModel("java",22,1));
        al.add(new CardModel("html",18,2));
        al.add(new CardModel("c--",18,2));

//        arrayAdapter = new ArrayAdapter<String>(this, R.layout.item, R.id.helloText, al);
        mAdapter=new MyCardAdapter(this,al);

        flingContainer.setAdapter(mAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                al.remove(0);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                makeToast(MyActivity.this, "不喜欢!");
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                makeToast(MyActivity.this, "喜欢!");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
                al.add(new CardModel("c#",18,2));
                mAdapter.notifyDataSetChanged();
                Log.d("LIST", "onAdapterAboutToEmpty");
                i++;
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                makeToast(MyActivity.this, "Clicked!");
            }
        });
        flingContainer.setMaxVisible(20);
    }

    static void makeToast(Context ctx, String s) {
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }

    public void click(View v) {
        switch (v.getId()) {
            case R.id.left:
                flingContainer.getTopCardListener().selectLeft();
                break;
            case R.id.right:
                flingContainer.getTopCardListener().selectRight();
                break;
        }
    }

}
