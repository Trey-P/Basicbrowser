package edu.temple.browserlab;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    PagerAdapter Page;
    //Tabs tabs[];
    Tabs tabfrag = new Tabs();
    Button button;
    int index=0,ilast=0;
    TextView Url;
    boolean firstWindow=true;
    ArrayList<Tabs> tabSet = new ArrayList();
    FragmentStatePagerAdapter fspa;
    FragmentManager fm = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager=(ViewPager) findViewById(R.id.viewPager);
        button = findViewById(R.id.Button);
        Url = findViewById(R.id.URLbox);
        tabfrag = Tabs.newInstance("https://www.Ign.com");
        tabSet.add(0,tabfrag);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstWindow)
                {
                    fm.beginTransaction().replace(R.id.viewPager, tabSet.get(0)).commit();
                    tabfrag.urlLoad(Url.getText().toString());
                    firstWindow=false;
                }
                else{
                     tabfrag=(Tabs) tabSet.get(index);
                     tabfrag.urlLoad(Url.getText().toString());
                    //Url.setText(tabfrag.currentURL);
                    fspa.notifyDataSetChanged();
                }
            }
        });

        //ActionBar---Inflate Menu

         fspa = new FragmentStatePagerAdapter(getSupportFragmentManager())
        {
            @Override
            public Fragment getItem(int i) {
               // fspa.startUpdate(tabSet.get(i).getView());
                ilast=i;
                return  tabSet.get(i);
            }

            @Override
            public int getCount() {
                //if(tabSet.get(index).getView()!=null)
                //fspa.finishUpdate(tabSet.get(index).getView());
                return tabSet.size();
            }
        };
        viewPager.setAdapter(fspa);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {


            }

            @Override
            public void onPageSelected(int i) {
                if(i>index) {
                    index = i;
                    Url.setText(tabSet.get(i).currentURL);
                }
                if(i<index) {
                    index = i;
                    Url.setText(tabSet.get(i).currentURL);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.browsermenu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();

        if(id == R.id.Forward){
            if(index==tabSet.size()-1)
            {
                Toast.makeText(this, "Your at the end of your Tab set", Toast.LENGTH_SHORT).show();
            }
            else
            {
                index=index+1;
              //  FragmentManager fm = getSupportFragmentManager();
               // fm.beginTransaction().replace(R.id.viewPager,fspa.getItem(index)).addToBackStack(null).commit();
                viewPager.setCurrentItem(index);
                fspa.notifyDataSetChanged();
                Tabs temp;
                temp=(Tabs) tabSet.get(index);
                Url.setText(temp.currentURL);
                fm.executePendingTransactions();
            }




        } else if (id == R.id.Newtab){
            Tabs frag;
            frag= Tabs.newInstance("https://www.Google.com");
            //fspa.startUpdate(tabSet.get(index).getView());
            index=index+1;
            tabSet.add(frag);
            fspa.notifyDataSetChanged();
            //FragmentManager fm = getSupportFragmentManager();
           // fm.beginTransaction().replace(R.id.viewPager, tabSet.get(tabSet.size()-1)).addToBackStack(null).commit();
            Toast.makeText(this, "New Tab is being generated!", Toast.LENGTH_SHORT).show();
            viewPager.setCurrentItem(index);
            Tabs temp;
            temp= tabSet.get(index);
            Url.setText(temp.currentURL);
            //fspa.finishUpdate(tabSet.get(index).getView());
        }
        else//Back button
        {

            //Checks if it is at original tab.
            if(index!=0){

                index=index-1;
                //FragmentManager fm = getSupportFragmentManager();
                //fm.beginTransaction().replace(R.id.viewPager,fspa.getItem(index)).addToBackStack(null).commit();
                viewPager.setCurrentItem(index);
                fspa.notifyDataSetChanged();
                Tabs temp= tabSet.get(index);
                Url.setText(temp.currentURL);
               // fm.executePendingTransactions();
            }
            else
            {
                Toast.makeText(this, "Currently on your first tab!", Toast.LENGTH_SHORT).show();
            }



        }

        return super.onOptionsItemSelected(item);
    }
}
