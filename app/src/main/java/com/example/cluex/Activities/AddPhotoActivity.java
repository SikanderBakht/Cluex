package com.example.cluex.Activities;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.cluex.R;

public class AddPhotoActivity extends AppCompatActivity {

    Button addPhotoButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        addPhotoButton=(Button) findViewById(R.id.addPhotoButton_id);



        addPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get existing constraints into a ConstraintSet
                ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.constraint_layout_id);

                ConstraintSet constraints = new ConstraintSet();
                constraints.clone(layout);
                // Define our ImageView and add it to layout
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setId(View.generateViewId());
                imageView.setImageResource(R.drawable.abc);
                layout.addView(imageView);

              /*  // Now constrain the ImageView so it is centered on the screen.
                // There is also a "center" method that can be used here.
                constraints.constrainWidth(imageView.getId(), ConstraintSet.WRAP_CONTENT);
                constraints.constrainHeight(imageView.getId(), ConstraintSet.WRAP_CONTENT);
                constraints.center(imageView.getId(), ConstraintSet.PARENT_ID, ConstraintSet.LEFT,
                        0, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0, 0.5f);
                constraints.center(imageView.getId(), ConstraintSet.PARENT_ID, ConstraintSet.TOP,
                        0, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0, 0.5f);  */

                constraints.connect(imageView.getId(), ConstraintSet.BOTTOM, addPhotoButton.getId(), ConstraintSet.TOP, 0);
                constraints.connect(imageView.getId(),ConstraintSet.RIGHT,ConstraintSet.PARENT_ID,ConstraintSet.RIGHT,0);
                constraints.connect(imageView.getId(),ConstraintSet.LEFT,ConstraintSet.PARENT_ID,ConstraintSet.LEFT,0);
                constraints.constrainHeight(imageView.getId(), 200);




                constraints.applyTo(layout);           }
        });




    }
}
