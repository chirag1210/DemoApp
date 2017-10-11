package katta.chirag.in.katta;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class DrawerFragment extends Fragment implements CallBack {

    private View view;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private DrawerAdapter drawerAdapter;
    private View containerView;
    private RecyclerView recyclerView;
    private String[] names = new String[]{"Item 1","Item 2","Item 3","Item 1","Item 2","Item 3","Item 1","Item 2","Item 3","Item 1","Item 2","Item 3"  };
    private int[] images = new int[]{R.drawable.friendlist, R.drawable.notification};
    ArrayList<DrawerModel> list;

    int mPosition;
    public DrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_drawer_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.listview);

        drawerAdapter = new DrawerAdapter(getActivity(),populateList(),DrawerFragment.this);

        recyclerView.setAdapter(drawerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //add ItemDecoration
     /*   recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));*/
        //or
        // recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        //or
        recyclerView.addItemDecoration(
                new DividerItemDecoration(getActivity(), R.drawable.my_divider));
        /*recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                openFragment(position);
                mDrawerLayout.closeDrawer(containerView);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));*/

        openFragment(0);

        return view;
    }

    private void openFragment(int position) {
        mPosition=position;

        switch (position) {
            case 0:
                removeAllFragment(new ContactUsFragment(), "Friends");

            default:
                removeAllFragment(new ContactUsFragment(), "Friends");
                break;
        }
    }

    public void removeAllFragment(Fragment replaceFragment, String tag) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        manager.popBackStackImmediate(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);

        Bundle bundle = new Bundle();
        bundle.putString("Category", list.get(mPosition).getName());
        replaceFragment.setArguments(bundle);

        ft.replace(R.id.container_body, replaceFragment);
        ft.commitAllowingStateLoss();
    }

    public void setUpDrawer(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open , R.string.drawer_close ) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    private ArrayList<DrawerModel> populateList(){

        list = new ArrayList<>();

        for(int i = 0; i < names.length; i++){
            DrawerModel drawerModel = new DrawerModel();
            drawerModel.setName(names[i]);
            list.add(drawerModel);
        }
        return list;
    }

    @Override
    public void onClickedItemPostion(int position) {
        openFragment(position);
        mDrawerLayout.closeDrawer(containerView);
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
