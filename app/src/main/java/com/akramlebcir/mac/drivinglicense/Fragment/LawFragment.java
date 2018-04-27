package com.akramlebcir.mac.drivinglicense.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.akramlebcir.mac.drivinglicense.R;
import com.akramlebcir.mac.drivinglicense.model.Infraction_detail;
import com.akramlebcir.mac.drivinglicense.model.Infraction_niv;

import java.util.ArrayList;
import java.util.List;

import iammert.com.expandablelib.ExpandCollapseListener;
import iammert.com.expandablelib.ExpandableLayout;
import iammert.com.expandablelib.Section;


public class LawFragment extends android.support.v4.app.Fragment {

    public LawFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_law, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ExpandableLayout layout = view.findViewById(R.id.expandable_layout);

        layout.setExpandListener(new ExpandCollapseListener.ExpandListener<Infraction_niv>() {
            @Override
            public void onExpanded(int i, Infraction_niv infraction_niv, View view) {
                view.findViewById(R.id.arrow).setBackgroundResource(R.drawable.ic_expand_more_black_24dp);
            }
        });

        layout.setCollapseListener(new ExpandCollapseListener.CollapseListener<Infraction_niv>() {
            @Override
            public void onCollapsed(int i, Infraction_niv infraction_niv, View view) {
                view.findViewById(R.id.arrow).setBackgroundResource(R.drawable.ic_chevron_right_black_24dp);
            }
        });

        layout.setRenderer(new ExpandableLayout.Renderer<Infraction_niv, Infraction_detail>() {
            @Override
            public void renderParent(View view, Infraction_niv infraction_niv, boolean b, int i) {
                ((TextView)view.findViewById(R.id.title_niv)).setText(infraction_niv.getName());
                //Toast.makeText(getContext(), "render "+i+" "+b, Toast.LENGTH_SHORT).show();
                view.findViewById(R.id.arrow).setBackgroundResource(b ? R.drawable.ic_expand_more_black_24dp : R.drawable.ic_chevron_right_black_24dp);
            }

            @Override
            public void renderChild(View view, Infraction_detail infraction_detail, int i, int i1) {
                TextView tv_title = view.findViewById(R.id.title_detail);
                TextView tv_price = view.findViewById(R.id.point_detail);
                TextView tv_point = view.findViewById(R.id.price_detail);
                tv_title.setText(infraction_detail.getTitle());
                tv_price.setText(infraction_detail.getPrice());
                tv_point.setText(infraction_detail.getPoint());
            }
        });
        
        layout.addSection(getSection("Infraction Niv 1"));
        layout.addSection(getSection("Infraction Niv 2"));
        layout.addSection(getSection("Infraction Niv 3"));
        layout.addSection(getSection("Infraction Niv 4"));
    }

    private Section<Infraction_niv,Infraction_detail> getSection(String title_niv) {
        Section<Infraction_niv,Infraction_detail> section = new Section<>();


        List<Infraction_detail> list = new ArrayList<>();
        for (int i=0;i<5;i++){
            list.add(new Infraction_detail("Infraction : "+(i+1),""+i,""+(i+100)));
        }

        Infraction_niv niv = new Infraction_niv(title_niv);
        List<Infraction_detail> list_detail = list;

        section.parent = niv;
        section.children.addAll(list_detail);
        return section;
    }
}
