package com.example.robert.lab1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;
import android.widget.Toast;


public class ListActivity extends AppCompatActivity {
    private static ExpandableListView expandableListView;
    private static ExpandableListAdapter adapter;
    /* ----------------------------------------------------------------------------------------------*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expandableListView = (ExpandableListView) findViewById(R.id.simple_expandable_listview);
        expandableListView.setGroupIndicator(null);
        onSetItems();
        onSetListeners();
    }
    /* ----------------------------------------------------------------------------------------------*/
    void onSetItems() {
        ArrayList<String> project = new ArrayList<>();
        List<String> teamMember1 = new ArrayList<>();
        List<String> teamMember2 = new ArrayList<>();
        List<String> teamMember3 = new ArrayList<>();
        List<String> teamMember4 = new ArrayList<>();
        List<String> teamMember5 = new ArrayList<>();
        // adds to hashmap the Project and children
        HashMap<String, List<String>> hashMap = new HashMap<>();
        for (int i = 1; i < 6; i++) {
            project.add("Project " + i);
        }
        for (int i = 1; i < 5; i++) {
            teamMember1.add("Project 1  - " + " : Member" + i);
        }

        for (int i = 1; i < 5; i++) {
            teamMember2.add("Project 2  - " + " : Member" + i);
        }

        for (int i = 1; i < 6; i++) {
            teamMember3.add("Project 3  - " + " : Member" + i);
        }

        for (int i = 1; i < 7; i++) {
            teamMember4.add("Project 4  - " + " : Member" + i);
        }
        for (int i = 1; i < 7; i++) {
            teamMember5.add("Project 5  - " + " : Member" + i);
        }
        // Adding header and childs to hash map
        hashMap.put(project.get(0), teamMember1);
        hashMap.put(project.get(1), teamMember2);
        hashMap.put(project.get(2), teamMember3);
        hashMap.put(project.get(3), teamMember4);
        hashMap.put(project.get(4), teamMember5);

        adapter = new ExpandableListAdapter(ListActivity.this, project, hashMap);
        expandableListView.setAdapter(adapter);
    }
/* ----------------------------------------------------------------------------------------------*/
    void onSetListeners() {
        expandableListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView listview, View view,
                                        int group_pos, long id) {
                Toast.makeText(ListActivity.this,
                         adapter.getGroup(group_pos).toString(),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        expandableListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView listview, View view,
                                        int groupPos, int childPos, long id) {
                Toast.makeText(
                        ListActivity.this,
                         ""+adapter.getChild(groupPos, childPos),
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

/*----------------------------------------------------------------------------------------------*/
    public class ExpandableListAdapter extends BaseExpandableListAdapter {
        private Context _context;
        private List<String> project;
        private HashMap<String, List<String>> groupMember;

        public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                     HashMap<String, List<String>> listChildData) {
            this._context = context;
            this.project = listDataHeader;
            this.groupMember = listChildData;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this.groupMember.get(this.project.get(groupPosition)).get(
                    childPosititon);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {

            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            // Getting child text
            final String childText = (String) getChild(groupPosition, childPosition);
            // Inflating child layout and setting textview
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.childs, parent, false);
            }

            TextView child_text = (TextView) convertView.findViewById(R.id.child);
            child_text.setText(childText);
            return convertView;
        }
        @Override
        public int getChildrenCount(int groupPosition) {

            // return children count
            return this.groupMember.get(this.project.get(groupPosition)).size();
        }
        @Override
        public Object getGroup(int groupPosition) {

            // Get header position
            return this.project.get(groupPosition);
        }
        @Override
        public int getGroupCount() {

            // Get header size
            return this.project.size();
        }
        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            // Getting header title
            String headerTitle = (String) getGroup(groupPosition);
            // Inflating header layout and setting text
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.header, parent, false);
            }
            TextView header_text = (TextView) convertView.findViewById(R.id.header);
            header_text.setText(headerTitle);
            // If group is expanded then change the text into bold and change the
            // icon
            if (isExpanded) {
                header_text.setTypeface(null, Typeface.BOLD);
            } else {
                // If group is not expanded then change the text back into normal
                // and change the icon
                header_text.setTypeface(null, Typeface.NORMAL);
            }
            return convertView;
        }
        @Override
        public boolean hasStableIds() {

            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {

            return true;
        }
    }
}
/*------------------------------------------------------------------------------------------------*/