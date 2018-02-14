package com.material.nereeducation.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.material.nereeducation.R;
import com.material.nereeducation.activity.bottomnavigation.BottomNavigationBasic;
import com.material.nereeducation.activity.bottomnavigation.BottomNavigationDark;
import com.material.nereeducation.activity.bottomnavigation.BottomNavigationIcon;
import com.material.nereeducation.activity.bottomnavigation.BottomNavigationLight;
import com.material.nereeducation.activity.bottomnavigation.BottomNavigationShifting;
import com.material.nereeducation.activity.bottomsheet.BottomSheetBasic;
import com.material.nereeducation.activity.bottomsheet.BottomSheetFloating;
import com.material.nereeducation.activity.bottomsheet.BottomSheetFull;
import com.material.nereeducation.activity.bottomsheet.BottomSheetList;
import com.material.nereeducation.activity.bottomsheet.BottomSheetMap;
import com.material.nereeducation.activity.button.ButtonBasic;
import com.material.nereeducation.activity.button.ButtonInUtilities;
import com.material.nereeducation.activity.button.FabMiddle;
import com.material.nereeducation.activity.button.FabMore;
import com.material.nereeducation.activity.button.FabMoreText;
import com.material.nereeducation.activity.card.CardBasic;
import com.material.nereeducation.activity.card.CardOverlap;
import com.material.nereeducation.activity.card.CardTimeline;
import com.material.nereeducation.activity.card.CardWizard;
import com.material.nereeducation.activity.card.CardWizardLight;
import com.material.nereeducation.activity.card.CardWizardOverlap;
import com.material.nereeducation.activity.chip.ChipBasic;
import com.material.nereeducation.activity.chip.ChipTag;
import com.material.nereeducation.activity.dialog.DialogAddPost;
import com.material.nereeducation.activity.dialog.DialogAddReview;
import com.material.nereeducation.activity.dialog.DialogBasic;
import com.material.nereeducation.activity.dialog.DialogCustom;
import com.material.nereeducation.activity.dialog.DialogCustomDark;
import com.material.nereeducation.activity.dialog.DialogCustomInfo;
import com.material.nereeducation.activity.dialog.DialogCustomLight;
import com.material.nereeducation.activity.dialog.DialogCustomWarning;
import com.material.nereeducation.activity.dialog.DialogFullscreen;
import com.material.nereeducation.activity.expansionpanel.ExpansionPanelBasic;
import com.material.nereeducation.activity.expansionpanel.ExpansionPanelInvoice;
import com.material.nereeducation.activity.expansionpanel.ExpansionPanelTicket;
import com.material.nereeducation.activity.form.FormLogin;
import com.material.nereeducation.activity.form.FormProfileData;
import com.material.nereeducation.activity.form.FormSignUp;
import com.material.nereeducation.activity.form.FormTextArea;
import com.material.nereeducation.activity.form.FormWithIcon;
import com.material.nereeducation.activity.gridlist.GridAlbums;
import com.material.nereeducation.activity.gridlist.GridBasic;
import com.material.nereeducation.activity.gridlist.GridCaller;
import com.material.nereeducation.activity.gridlist.GridSectioned;
import com.material.nereeducation.activity.gridlist.GridSingleLine;
import com.material.nereeducation.activity.gridlist.GridTwoLine;
import com.material.nereeducation.activity.list.ListAnimation;
import com.material.nereeducation.activity.list.ListBasic;
import com.material.nereeducation.activity.list.ListDrag;
import com.material.nereeducation.activity.list.ListExpand;
import com.material.nereeducation.activity.list.ListMultiSelection;
import com.material.nereeducation.activity.list.ListSectioned;
import com.material.nereeducation.activity.list.ListSwipe;
import com.material.nereeducation.activity.noitem.NoItemArchived;
import com.material.nereeducation.activity.noitem.NoItemBgCactus;
import com.material.nereeducation.activity.noitem.NoItemBgCity;
import com.material.nereeducation.activity.noitem.NoItemInternetIcon;
import com.material.nereeducation.activity.noitem.NoItemInternetImage;
import com.material.nereeducation.activity.noitem.NoItemSearch;
import com.material.nereeducation.activity.noitem.NoItemTabs;
import com.material.nereeducation.activity.picker.PickerColor;
import com.material.nereeducation.activity.picker.PickerDateDark;
import com.material.nereeducation.activity.picker.PickerDateLight;
import com.material.nereeducation.activity.picker.PickerLocation;
import com.material.nereeducation.activity.picker.PickerTimeDark;
import com.material.nereeducation.activity.picker.PickerTimeLight;
import com.material.nereeducation.activity.player.PlayerMusicAlbumCircle;
import com.material.nereeducation.activity.player.PlayerMusicAlbumDark;
import com.material.nereeducation.activity.player.PlayerMusicAlbumGrid;
import com.material.nereeducation.activity.player.PlayerMusicAlbumSimple;
import com.material.nereeducation.activity.player.PlayerMusicBasic;
import com.material.nereeducation.activity.player.PlayerMusicLight;
import com.material.nereeducation.activity.player.PlayerMusicSongList;
import com.material.nereeducation.activity.player.PlayerMusicTabs;
import com.material.nereeducation.activity.player.PlayerMusicTabsIcon;
import com.material.nereeducation.activity.profile.TutorProfileDetails;
import com.material.nereeducation.activity.progressactivity.ProgressBasic;
import com.material.nereeducation.activity.progressactivity.ProgressCircleCenter;
import com.material.nereeducation.activity.progressactivity.ProgressLinearCenter;
import com.material.nereeducation.activity.progressactivity.ProgressLinearTop;
import com.material.nereeducation.activity.progressactivity.ProgressOnScroll;
import com.material.nereeducation.activity.progressactivity.ProgressPullRefresh;
import com.material.nereeducation.activity.search.SearchCity;
import com.material.nereeducation.activity.search.SearchHistoryCard;
import com.material.nereeducation.activity.search.SearchPrimary;
import com.material.nereeducation.activity.search.SearchPrimaryBg;
import com.material.nereeducation.activity.search.SearchStore;
import com.material.nereeducation.activity.search.SearchToolbarDark;
import com.material.nereeducation.activity.search.SearchToolbarLight;
import com.material.nereeducation.activity.shopping.ShoppingCategoryCard;
import com.material.nereeducation.activity.shopping.ShoppingCategoryImage;
import com.material.nereeducation.activity.shopping.ShoppingCategoryList;
import com.material.nereeducation.activity.shopping.ShoppingCheckoutCard;
import com.material.nereeducation.activity.shopping.ShoppingProductAdvDetails;
import com.material.nereeducation.activity.shopping.ShoppingProductDetails;
import com.material.nereeducation.activity.shopping.ShoppingProductGrid;
import com.material.nereeducation.activity.shopping.ShoppingSubCategoryTabs;
import com.material.nereeducation.activity.slider.SliderColorPicker;
import com.material.nereeducation.activity.slider.SliderDark;
import com.material.nereeducation.activity.slider.SliderLight;
import com.material.nereeducation.activity.sliderimage.SliderImageCard;
import com.material.nereeducation.activity.sliderimage.SliderImageCardAuto;
import com.material.nereeducation.activity.sliderimage.SliderImageHeader;
import com.material.nereeducation.activity.sliderimage.SliderImageHeaderAuto;
import com.material.nereeducation.activity.snackbartoast.SnackbarAndFab;
import com.material.nereeducation.activity.snackbartoast.SnackbarToastBasic;
import com.material.nereeducation.activity.stepper.StepperDots;
import com.material.nereeducation.activity.stepper.StepperProgress;
import com.material.nereeducation.activity.stepper.StepperText;
import com.material.nereeducation.activity.stepper.StepperVertical;
import com.material.nereeducation.activity.stepper.StepperWizardColor;
import com.material.nereeducation.activity.stepper.StepperWizardLight;
import com.material.nereeducation.activity.tabs.TabsBasic;
import com.material.nereeducation.activity.tabs.TabsDark;
import com.material.nereeducation.activity.tabs.TabsIcon;
import com.material.nereeducation.activity.tabs.TabsIconLight;
import com.material.nereeducation.activity.tabs.TabsIconStack;
import com.material.nereeducation.activity.tabs.TabsLight;
import com.material.nereeducation.activity.tabs.TabsRound;
import com.material.nereeducation.activity.tabs.TabsScroll;
import com.material.nereeducation.activity.tabs.TabsStore;
import com.material.nereeducation.activity.tabs.TabsTextIcon;
import com.material.nereeducation.activity.timeline.TimelineDotCard;
import com.material.nereeducation.activity.timeline.TimelineFeed;
import com.material.nereeducation.activity.timeline.TimelinePath;
import com.material.nereeducation.activity.timeline.TimelineSimple;
import com.material.nereeducation.activity.timeline.TimelineTwitter;
import com.material.nereeducation.activity.toolbar.ToolbarBasic;
import com.material.nereeducation.activity.toolbar.ToolbarCollapse;
import com.material.nereeducation.activity.toolbar.ToolbarCollapsePin;
import com.material.nereeducation.activity.toolbar.ToolbarDark;
import com.material.nereeducation.activity.toolbar.ToolbarLight;
import com.material.nereeducation.adapter.ExpandableRecyclerAdapter;
import com.material.nereeducation.adapter.MainMenuAdapter;
import com.material.nereeducation.model.MenuType;
import com.material.nereeducation.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class MainMenu extends AppCompatActivity {

    private RecyclerView recycler;
    private MainMenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponentMenu();
        Tools.setSystemBarColor(this, R.color.grey_1000);
    }

    private void initComponentMenu() {
        recycler = (RecyclerView) findViewById(R.id.main_recycler);
        adapter = new MainMenuAdapter(this, generateMenuItems(), new MainMenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int itemId) {
                onMenuItemSelected(itemId);
            }
        });

        adapter.setMode(ExpandableRecyclerAdapter.MODE_ACCORDION);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setNestedScrollingEnabled(false);
        recycler.setAdapter(adapter);
    }

    private void onMenuItemSelected(int itemId) {
        switch (itemId) {
            // Bottom Navigation -------------------------------------------------------------------
            case 101:
                startActivity(new Intent(this, BottomNavigationBasic.class));
                break;
            case 102:
                startActivity(new Intent(this, BottomNavigationShifting.class));
                break;
            case 103:
                startActivity(new Intent(this, BottomNavigationLight.class));
                break;
            case 104:
                startActivity(new Intent(this, BottomNavigationDark.class));
                break;
            case 105:
                startActivity(new Intent(this, BottomNavigationIcon.class));
                break;

            // Bottom Sheet ------------------------------------------------------------------------
            case 201:
                startActivity(new Intent(this, BottomSheetBasic.class));
                break;
            case 202:
                startActivity(new Intent(this, BottomSheetList.class));
                break;
            case 203:
                startActivity(new Intent(this, BottomSheetMap.class));
                break;
            case 204:
                startActivity(new Intent(this, BottomSheetFloating.class));
                break;
            case 205:
                startActivity(new Intent(this, BottomSheetFull.class));
                break;

            // Buttons -----------------------------------------------------------------------------
            case 301:
                startActivity(new Intent(this, ButtonBasic.class));
                break;
            case 302:
                startActivity(new Intent(this, ButtonInUtilities.class));
                break;
            case 303:
                startActivity(new Intent(this, FabMiddle.class));
                break;
            case 304:
                startActivity(new Intent(this, FabMore.class));
                break;
            case 305:
                startActivity(new Intent(this, FabMoreText.class));
                break;

            // Cards -------------------------------------------------------------------------------
            case 401:
                startActivity(new Intent(this, CardBasic.class));
                break;
            case 402:
                startActivity(new Intent(this, CardTimeline.class));
                break;
            case 403:
                startActivity(new Intent(this, CardOverlap.class));
                break;
            case 404:
                startActivity(new Intent(this, CardWizard.class));
                break;
            case 405:
                startActivity(new Intent(this, CardWizardLight.class));
                break;
            case 406:
                startActivity(new Intent(this, CardWizardOverlap.class));
                break;

            // Chips -------------------------------------------------------------------------------
            case 501:
                startActivity(new Intent(this, ChipBasic.class));
                break;
            case 502:
                startActivity(new Intent(this, ChipTag.class));
                break;

            // Dialogs -----------------------------------------------------------------------------
            case 601:
                startActivity(new Intent(this, DialogBasic.class));
                break;
            case 602:
                startActivity(new Intent(this, DialogFullscreen.class));
                break;
            case 603:
                startActivity(new Intent(this, DialogCustom.class));
                break;
            case 604:
                startActivity(new Intent(this, DialogCustomInfo.class));
                break;
            case 605:
                startActivity(new Intent(this, DialogCustomWarning.class));
                break;
            case 606:
                startActivity(new Intent(this, DialogCustomLight.class));
                break;
            case 607:
                startActivity(new Intent(this, DialogCustomDark.class));
                break;
            case 608:
                startActivity(new Intent(this, DialogAddPost.class));
                break;
            case 609:
                startActivity(new Intent(this, DialogAddReview.class));
                break;

            // Expansion Panels --------------------------------------------------------------------
            case 701:
                startActivity(new Intent(this, ExpansionPanelBasic.class));
                break;
            case 702:
                startActivity(new Intent(this, ExpansionPanelInvoice.class));
                break;
            case 703:
                startActivity(new Intent(this, ExpansionPanelTicket.class));
                break;

            // Grid Lists --------------------------------------------------------------------------
            case 801:
                startActivity(new Intent(this, GridBasic.class));
                break;
            case 802:
                startActivity(new Intent(this, GridSingleLine.class));
                break;
            case 803:
                startActivity(new Intent(this, GridTwoLine.class));
                break;
            case 804:
                startActivity(new Intent(this, GridSectioned.class));
                break;
            case 805:
                startActivity(new Intent(this, GridAlbums.class));
                break;
            case 806:
                startActivity(new Intent(this, GridCaller.class));
                break;

            // Lists -------------------------------------------------------------------------------
            case 901:
                startActivity(new Intent(this, ListBasic.class));
                break;
            case 902:
                startActivity(new Intent(this, ListSectioned.class));
                break;
            case 903:
                startActivity(new Intent(this, ListAnimation.class));
                break;
            case 904:
                startActivity(new Intent(this, ListExpand.class));
                break;
            case 905:
                startActivity(new Intent(this, ListDrag.class));
                break;
            case 906:
                startActivity(new Intent(this, ListSwipe.class));
                break;
            case 907:
                startActivity(new Intent(this, ListMultiSelection.class));
                break;


            // Pickers -----------------------------------------------------------------------------
            case 1001:
                startActivity(new Intent(this, PickerDateLight.class));
                break;
            case 1002:
                startActivity(new Intent(this, PickerDateDark.class));
                break;
            case 1003:
                startActivity(new Intent(this, PickerTimeLight.class));
                break;
            case 1004:
                startActivity(new Intent(this, PickerTimeDark.class));
                break;
            case 1005:
                startActivity(new Intent(this, PickerColor.class));
                break;
            case 1006:
                startActivity(new Intent(this, PickerLocation.class));
                break;

            // Progress & Activity -----------------------------------------------------------------
            case 1101:
                startActivity(new Intent(this, ProgressBasic.class));
                break;
            case 1102:
                startActivity(new Intent(this, ProgressLinearCenter.class));
                break;
            case 1103:
                startActivity(new Intent(this, ProgressLinearTop.class));
                break;
            case 1104:
                startActivity(new Intent(this, ProgressCircleCenter.class));
                break;
            case 1105:
                startActivity(new Intent(this, ProgressOnScroll.class));
                break;
            case 1106:
                startActivity(new Intent(this, ProgressPullRefresh.class));
                break;

            // Sliders -----------------------------------------------------------------------------
            case 1201:
                startActivity(new Intent(this, SliderLight.class));
                break;
            case 1202:
                startActivity(new Intent(this, SliderDark.class));
                break;
            case 1203:
                startActivity(new Intent(this, SliderColorPicker.class));
                break;

            // Snackbars & Toasts ------------------------------------------------------------------
            case 1301:
                startActivity(new Intent(this, SnackbarToastBasic.class));
                break;
            case 1302:
                startActivity(new Intent(this, SnackbarAndFab.class));
                break;

            // Steppers ----------------------------------------------------------------------------
            case 1401:
                startActivity(new Intent(this, StepperText.class));
                break;
            case 1402:
                startActivity(new Intent(this, StepperDots.class));
                break;
            case 1403:
                startActivity(new Intent(this, StepperProgress.class));
                break;
            case 1404:
                startActivity(new Intent(this, StepperVertical.class));
                break;
            case 1405:
                startActivity(new Intent(this, StepperWizardLight.class));
                break;
            case 1406:
                startActivity(new Intent(this, StepperWizardColor.class));
                break;

            // Tabs --------------------------------------------------------------------------------
            case 1501:
                startActivity(new Intent(this, TabsBasic.class));
                break;
            case 1502:
                startActivity(new Intent(this, TabsStore.class));
                break;
            case 1503:
                startActivity(new Intent(this, TabsLight.class));
                break;
            case 1504:
                startActivity(new Intent(this, TabsDark.class));
                break;
            case 1505:
                startActivity(new Intent(this, TabsIcon.class));
                break;
            case 1506:
                startActivity(new Intent(this, TabsTextIcon.class));
                break;
            case 1507:
                startActivity(new Intent(this, TabsIconLight.class));
                break;
            case 1508:
                startActivity(new Intent(this, TabsIconStack.class));
                break;
            case 1509:
                startActivity(new Intent(this, TabsScroll.class));
                break;
            case 1510:
                startActivity(new Intent(this, TabsRound.class));
                break;

            // Form --------------------------------------------------------------------------------
            case 1601:
                startActivity(new Intent(this, FormLogin.class));
                break;
            case 1602:
                startActivity(new Intent(this, FormSignUp.class));
                break;
            case 1603:
                startActivity(new Intent(this, FormProfileData.class));
                break;
            case 1604:
                startActivity(new Intent(this, FormWithIcon.class));
                break;
            case 1605:
                startActivity(new Intent(this, FormTextArea.class));
                break;

            // Toolbars ----------------------------------------------------------------------------
            case 1701:
                startActivity(new Intent(this, ToolbarBasic.class));
                break;
            case 1702:
                startActivity(new Intent(this, ToolbarCollapse.class));
                break;
            case 1703:
                startActivity(new Intent(this, ToolbarCollapsePin.class));
                break;
            case 1704:
                startActivity(new Intent(this, ToolbarLight.class));
                break;
            case 1705:
                startActivity(new Intent(this, ToolbarDark.class));
                break;

            // Profile -----------------------------------------------------------------------------
            case 1801:
                startActivity(new Intent(this, TutorProfileDetails.class));
                break;
            // No Item Page ------------------------------------------------------------------------
            case 19001:
                startActivity(new Intent(this, NoItemArchived.class));
                break;
            case 19002:
                startActivity(new Intent(this, NoItemSearch.class));
                break;
            case 19003:
                startActivity(new Intent(this, NoItemInternetIcon.class));
                break;
            case 19004:
                startActivity(new Intent(this, NoItemInternetImage.class));
                break;
            case 19005:
                startActivity(new Intent(this, NoItemBgCity.class));
                break;
            case 19006:
                startActivity(new Intent(this, NoItemBgCactus.class));
                break;
            case 19007:
                startActivity(new Intent(this, NoItemTabs.class));
                break;

            // Player ------------------------------------------------------------------------------
            case 20001:
                startActivity(new Intent(this, PlayerMusicBasic.class));
                break;
            case 20002:
                startActivity(new Intent(this, PlayerMusicLight.class));
                break;
            case 20003:
                startActivity(new Intent(this, PlayerMusicAlbumDark.class));
                break;
            case 20004:
                startActivity(new Intent(this, PlayerMusicAlbumCircle.class));
                break;
            case 20005:
                startActivity(new Intent(this, PlayerMusicAlbumSimple.class));
                break;
            case 20006:
                startActivity(new Intent(this, PlayerMusicSongList.class));
                break;
            case 20007:
                startActivity(new Intent(this, PlayerMusicAlbumGrid.class));
                break;
            case 20008:
                startActivity(new Intent(this, PlayerMusicTabs.class));
                break;
            case 20009:
                startActivity(new Intent(this, PlayerMusicTabsIcon.class));
                break;

            // Timeline ----------------------------------------------------------------------------
            case 21001:
                startActivity(new Intent(this, TimelineFeed.class));
                break;
            case 21002:
                startActivity(new Intent(this, TimelinePath.class));
                break;
            case 21003:
                startActivity(new Intent(this, TimelineDotCard.class));
                break;
            case 21004:
                startActivity(new Intent(this, TimelineTwitter.class));
                break;
            case 21005:
                startActivity(new Intent(this, TimelineSimple.class));
                break;

            // Shopping ----------------------------------------------------------------------------
            case 22001:
                startActivity(new Intent(this, ShoppingCategoryList.class));
                break;
            case 22002:
                startActivity(new Intent(this, ShoppingCategoryCard.class));
                break;
            case 22003:
                startActivity(new Intent(this, ShoppingCategoryImage.class));
                break;
            case 22004:
                startActivity(new Intent(this, ShoppingSubCategoryTabs.class));
                break;
            case 22005:
                startActivity(new Intent(this, ShoppingProductGrid.class));
                break;
            case 22006:
                startActivity(new Intent(this, ShoppingProductDetails.class));
                break;
            case 22007:
                startActivity(new Intent(this, ShoppingProductAdvDetails.class));
                break;
            case 22008:
                startActivity(new Intent(this, ShoppingCheckoutCard.class));
                break;

            // Search Page -------------------------------------------------------------------------
            case 23001:
                startActivity(new Intent(this, SearchToolbarLight.class));
                break;
            case 23002:
                startActivity(new Intent(this, SearchToolbarDark.class));
                break;
            case 23003:
                startActivity(new Intent(this, SearchStore.class));
                break;
            case 23004:
                startActivity(new Intent(this, SearchPrimary.class));
                break;
            case 23005:
                startActivity(new Intent(this, SearchPrimaryBg.class));
                break;
            case 23006:
                startActivity(new Intent(this, SearchHistoryCard.class));
                break;
            case 23007:
                startActivity(new Intent(this, SearchCity.class));
                break;

            // Slider Image ------------------------------------------------------------------------
            case 24001:
                startActivity(new Intent(this, SliderImageHeader.class));
                break;
            case 24002:
                startActivity(new Intent(this, SliderImageHeaderAuto.class));
                break;
            case 24003:
                startActivity(new Intent(this, SliderImageCard.class));
                break;
            case 24004:
                startActivity(new Intent(this, SliderImageCardAuto.class));
                break;

            // about -------------------------------------------------------------------------------
            case 90:
                showDialogAbout();
                break;
        }
    }

    private List<MainMenuAdapter.ListItem> generateMenuItems() {
        List<MainMenuAdapter.ListItem> items = new ArrayList<>();

        items.add(new MainMenuAdapter.ListItem(-1, null, -1, MenuType.DIVIDER));
        items.add(new MainMenuAdapter.ListItem(100, "Bottom Navigation", R.drawable.ic_view_column, MenuType.HEADER));
        items.add(new MainMenuAdapter.ListItem(101, "Basic", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(102, "Shifting", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(103, "Light", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(104, "Dark", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(105, "Icon", -1, MenuType.SUB_HEADER));

        items.add(new MainMenuAdapter.ListItem(200, "Bottom Sheet", R.drawable.ic_call_to_actio, MenuType.HEADER));
        items.add(new MainMenuAdapter.ListItem(201, "Basic", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(202, "List", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(203, "Map", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(204, "Floating", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(205, "Full", -1, MenuType.SUB_HEADER));

        items.add(new MainMenuAdapter.ListItem(300, "Buttons", R.drawable.ic_touch_app, MenuType.HEADER));
        items.add(new MainMenuAdapter.ListItem(301, "Basic", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(302, "Button In Utilities", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(303, "Fab Middle", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(304, "Fab More", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(305, "Fab More Text", -1, MenuType.SUB_HEADER));

        items.add(new MainMenuAdapter.ListItem(400, "Cards", R.drawable.ic_note, MenuType.HEADER));
        items.add(new MainMenuAdapter.ListItem(401, "Basic", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(402, "Timeline", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(403, "Overlap", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(404, "Wizard", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(405, "Wizard Light", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(406, "Wizard Overlap", -1, MenuType.SUB_HEADER));

        items.add(new MainMenuAdapter.ListItem(500, "Chips", R.drawable.ic_label, MenuType.HEADER));
        items.add(new MainMenuAdapter.ListItem(501, "Basic", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(502, "Tag", -1, MenuType.SUB_HEADER));

        items.add(new MainMenuAdapter.ListItem(600, "Dialogs", R.drawable.ic_picture_in_picture, MenuType.HEADER));
        items.add(new MainMenuAdapter.ListItem(601, "Basic", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(602, "Fullscreen", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(603, "Custom", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(604, "Custom Info", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(605, "Custom Warning", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(606, "Custom Light", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(607, "Custom Dark", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(608, "Custom Add Post", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(609, "Custom Add Review", -1, MenuType.SUB_HEADER));

        items.add(new MainMenuAdapter.ListItem(700, "Expansion Panels", R.drawable.ic_arrow_downward, MenuType.HEADER));
        items.add(new MainMenuAdapter.ListItem(701, "Basic", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(702, "Invoice", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(703, "Ticket", -1, MenuType.SUB_HEADER));

        items.add(new MainMenuAdapter.ListItem(800, "Grid", R.drawable.ic_apps, MenuType.HEADER));
        items.add(new MainMenuAdapter.ListItem(801, "Basic", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(802, "Single Line", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(803, "Two Line", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(804, "Sectioned", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(805, "Albums", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(806, "Caller", -1, MenuType.SUB_HEADER));

        items.add(new MainMenuAdapter.ListItem(900, "Lists", R.drawable.ic_view_stream, MenuType.HEADER));
        items.add(new MainMenuAdapter.ListItem(901, "Basic", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(902, "Sectioned", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(903, "Animation", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(904, "Expand", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(905, "Draggable", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(906, "Swipe", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(907, "Multi Selection", -1, MenuType.SUB_HEADER));

        items.add(new MainMenuAdapter.ListItem(2000, "Menu", R.drawable.ic_reoder, MenuType.HEADER));
        items.add(new MainMenuAdapter.ListItem(2001, "Drawer News", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(2002, "Drawer Mail", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(2003, "Drawer Simple Light", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(2004, "Drawer Simple Dark", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(2005, "Drawer No Icon", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(2006, "Overflow Toolbar", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(2007, "Overflow List", -1, MenuType.SUB_HEADER));

        items.add(new MainMenuAdapter.ListItem(1000, "Pickers", R.drawable.ic_event, MenuType.HEADER));
        items.add(new MainMenuAdapter.ListItem(1001, "Date Light", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1002, "Date Dark", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1003, "Time Light", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1004, "Time Dark", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1005, "Color RGB", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1006, "Location", -1, MenuType.SUB_HEADER));

        items.add(new MainMenuAdapter.ListItem(1100, "Progress & Activity", R.drawable.ic_settings_backup_restore, MenuType.HEADER));
        items.add(new MainMenuAdapter.ListItem(1101, "Basic", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1102, "Linear Center", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1103, "Linear Top", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1104, "Circle Center", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1105, "On Scroll", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1106, "Pull Refresh", -1, MenuType.SUB_HEADER));

        items.add(new MainMenuAdapter.ListItem(1200, "Sliders", R.drawable.ic_tune, MenuType.HEADER));
        items.add(new MainMenuAdapter.ListItem(1201, "Light", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1202, "Dark", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1203, "Color Picker", -1, MenuType.SUB_HEADER));

        items.add(new MainMenuAdapter.ListItem(1300, "Snackbars & Toasts", R.drawable.ic_wb_iridescent, MenuType.HEADER));
        items.add(new MainMenuAdapter.ListItem(1301, "Basic", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1302, "Lift FAB", -1, MenuType.SUB_HEADER));

        items.add(new MainMenuAdapter.ListItem(1400, "Steppers", R.drawable.ic_timeline, MenuType.HEADER));
        items.add(new MainMenuAdapter.ListItem(1401, "Text", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1402, "Dots", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1403, "Progress", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1404, "Vertical", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1405, "Wizard Light", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1406, "Wizard Color", -1, MenuType.SUB_HEADER));

        items.add(new MainMenuAdapter.ListItem(1500, "Tabs", R.drawable.ic_tabs, MenuType.HEADER));
        items.add(new MainMenuAdapter.ListItem(1501, "Basic", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1502, "Store", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1503, "Light", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1504, "Dark", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1505, "Icon", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1506, "Text & Icon", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1507, "Icon Light", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1508, "Icon Stack", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1509, "Scroll", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1510, "Round", -1, MenuType.SUB_HEADER));

        items.add(new MainMenuAdapter.ListItem(1600, "Form", R.drawable.ic_assignment, MenuType.HEADER));
        items.add(new MainMenuAdapter.ListItem(1601, "Login", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1602, "Sign Up", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1603, "Profile Data", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1604, "With Icon", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1605, "Text Area", -1, MenuType.SUB_HEADER));

        items.add(new MainMenuAdapter.ListItem(1700, "Toolbars", R.drawable.ic_web_asset, MenuType.HEADER));
        items.add(new MainMenuAdapter.ListItem(1701, "Basic", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1702, "Collapse", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1703, "Collapse And Pin", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1704, "Light", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1705, "Dark", -1, MenuType.SUB_HEADER));

        items.add(new MainMenuAdapter.ListItem(-1, "Extra", -1, MenuType.DIVIDER));

        items.add(new MainMenuAdapter.ListItem(1800, "Profile", R.drawable.ic_person, MenuType.HEADER));
        items.add(new MainMenuAdapter.ListItem(1801, "Polygon", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1802, "Purple", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1803, "Red", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1804, "Blue Appbar", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1805, "Image Appbar", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1806, "Drawer Simple", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1807, "Drawer Image", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1808, "Gallery", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(1809, "Gallery Two", -1, MenuType.SUB_HEADER));

        items.add(new MainMenuAdapter.ListItem(19000, "No Item Page", R.drawable.ic_do_not_disturb_off, MenuType.HEADER));
        items.add(new MainMenuAdapter.ListItem(19001, "Archived", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(19002, "Search", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(19003, "Internet Icon", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(19004, "Internet Image", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(19005, "Bg City", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(19006, "Bg Cactus", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(19007, "Tabs", -1, MenuType.SUB_HEADER));

        items.add(new MainMenuAdapter.ListItem(20000, "Player", R.drawable.ic_live_tv, MenuType.HEADER));
        items.add(new MainMenuAdapter.ListItem(20001, "Music Basic", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(20002, "Music Light", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(20003, "Music Album Dark", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(20004, "Music Album Circle", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(20005, "Music Album Simple", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(20006, "Music Song List", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(20007, "Music Album Grid", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(20008, "Music Tabs", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(20009, "Music Tabs Icon", -1, MenuType.SUB_HEADER));

        items.add(new MainMenuAdapter.ListItem(21000, "Timeline", R.drawable.ic_wrap_text, MenuType.HEADER));
        items.add(new MainMenuAdapter.ListItem(21001, "Timeline Feed", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(21002, "Timeline Path", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(21003, "Timeline Dot Card", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(21004, "Timeline Twitter", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(21005, "Timeline Simple", -1, MenuType.SUB_HEADER));

        items.add(new MainMenuAdapter.ListItem(22000, "Shopping", R.drawable.ic_shopping_cart, MenuType.HEADER));
        items.add(new MainMenuAdapter.ListItem(22001, "Category List", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(22002, "Category Card", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(22003, "Category Image", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(22004, "Sub Category Tabs", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(22005, "Product Grid", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(22006, "Product Details", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(22007, "Product Adv Details", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(22008, "Checkout Card", -1, MenuType.SUB_HEADER));

        items.add(new MainMenuAdapter.ListItem(23000, "Search Page", R.drawable.ic_search, MenuType.HEADER));
        items.add(new MainMenuAdapter.ListItem(23001, "Toolbar Light", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(23002, "Toolbar Dark", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(23003, "Store", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(23004, "Primary", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(23005, "Primary Bg", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(23006, "History Card", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(23007, "City", -1, MenuType.SUB_HEADER));

        items.add(new MainMenuAdapter.ListItem(24000, "Slider Image", R.drawable.ic_photo_library, MenuType.HEADER));
        items.add(new MainMenuAdapter.ListItem(24001, "Header", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(24002, "Header Auto", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(24003, "Card", -1, MenuType.SUB_HEADER));
        items.add(new MainMenuAdapter.ListItem(24004, "Card Auto", -1, MenuType.SUB_HEADER));

        items.add(new MainMenuAdapter.ListItem(-1, "Application", -1, MenuType.DIVIDER));
        items.add(new MainMenuAdapter.ListItem(90, "About", R.drawable.ic_error_outline, MenuType.NORMAL));

        return items;
    }
//
//    @Override
//    public void onBackPressed() {
//        doExitApp();
//    }

    private long exitTime = 0;

    public void doExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "Press again to exit app", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    private void showDialogAbout() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_about);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ((AppCompatButton) dialog.findViewById(R.id.bt_getcode)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://codecanyon.net/user/dream_space/portfolio"));
                startActivity(i);
            }
        });

        ((AppCompatButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

}
