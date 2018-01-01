package com.material.components.activity.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.material.components.R;
import com.material.components.activity.MainMenu;
import com.material.components.activity.list.ListMultiSelection;
import com.material.components.activity.login.LoginActivity;
import com.material.components.activity.login.SQLiteHandler;
import com.material.components.activity.login.SessionManager;
import com.material.components.activity.profile.ProfilePolygon;
import com.material.components.activity.search.SearchToolbarLight;
import com.material.components.activity.toolbar.ToolbarCollapsePin;
import com.material.components.adapter.AdapterGridShopProductCard;
import com.material.components.data.DataGenerator;
import com.material.components.model.ShopCategory;
import com.material.components.model.ShopProduct;
import com.material.components.utils.Tools;
import com.material.components.widget.SpacingItemDecoration;

import java.util.HashMap;
import java.util.List;

public class MenuDrawerNews extends AppCompatActivity {

    private ActionBar actionBar;
    private Toolbar toolbar;

    private RecyclerView recyclerView;
    private AdapterGridShopProductCard mAdapter;
    private View parent_view;

    private SessionManager session;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_drawer_news);
        parent_view = findViewById(R.id.parent_view);

        initToolbar();
        initNavigationMenu();
        initComponent();

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Dashboard");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_search)
        {
            Intent gotoSearch = new Intent(getApplicationContext(), SearchToolbarLight.class);
            startActivity(gotoSearch);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_basic, menu);
        return true;
    }

    private void initNavigationMenu() {
        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem item) {

                int id = item.getItemId();

                if(id == R.id.nav_profile)
                {
                    Intent intentProfile = new Intent(getApplicationContext(), ProfilePolygon.class);
                    startActivity(intentProfile);
                }

                if(id == R.id.nav_trending)
                {
                    Intent intentProfile = new Intent(getApplicationContext(), MainMenu.class);
                    startActivity(intentProfile);
                }
                if(id == R.id.logout)
                {
                    logoutUser();
                }
                drawer.closeDrawers();
                return true;
            }
        });
    }


    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MenuDrawerNews.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void initComponent() {

        List<ShopProduct> items = DataGenerator.getShoppingProduct(this);

        //set data and list adapter
        mAdapter = new AdapterGridShopProductCard(this, items);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(mAdapter);

        RecyclerView.LayoutManager layoutManagerSubject = new LinearLayoutManager(getApplicationContext());
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(this, 8), true));
        recyclerView.setLayoutManager(layoutManagerSubject);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManagerSubject = (LinearLayoutManager) layoutManagerSubject;
        linearLayoutManagerSubject.setOrientation(LinearLayoutManager.HORIZONTAL);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterGridShopProductCard.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ShopProduct obj, int position) {
                Snackbar.make(parent_view, "Item " + obj.title + " clicked", Snackbar.LENGTH_SHORT).show();
                Intent gotoChapter = new Intent(getApplicationContext(), ListMultiSelection.class);
                startActivity(gotoChapter);
            }
        });

        mAdapter.setOnMoreButtonClickListener(new AdapterGridShopProductCard.OnMoreButtonClickListener() {
            @Override
            public void onItemClick(View view, ShopProduct obj, MenuItem item) {
                Snackbar.make(parent_view, obj.title + " (" + item.getTitle() + ") clicked", Snackbar.LENGTH_SHORT).show();
            }
        });

    }
}
