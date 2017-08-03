package com.example.cluex.Activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cluex.Helper.ContactHelper;
import com.example.cluex.Helper.SetContactData;
import com.example.cluex.R;

import java.util.ArrayList;

import static android.R.attr.value;

public class MobileContactsActivity extends ListActivity {
    //private ArrayList<String> conNames;
    //private ArrayList<String> conNumbers;
    private ArrayList<SetContactData> conCont;
    private ArrayList<SetContactData> array_sort;
    private Cursor crContacts;
    EditText inputSearch;
  //  private ArrayList<String> conNamesDummy;
   // private ArrayList<String> conNumbersDummy;
    int textlength = 0;


    private Button button3,button2;
    private ListView lv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_contacts);

        conCont=new ArrayList<SetContactData>();
        array_sort=new ArrayList<SetContactData>();

       // conNames = new ArrayList<String>();
       // conNumbers = new ArrayList<String>();
        //	button3 = (Button) findViewById(R.id.button3);
        inputSearch = (EditText) findViewById(R.id.inputSearch);

        button2=(Button) findViewById(R.id.button2);
        lv = (ListView) findViewById(android.R.id.list);


        //
        //	button2.setOnClickListener(new View.OnClickListener() {
        //		@Override
        //		public void onClick(View v) {
        //			deleteContacts(v);			}
        //	});

        crContacts = ContactHelper.getContactCursor(getContentResolver(), "");
        crContacts.moveToFirst();

        while (!crContacts.isAfterLast()) {
       //     conNames.add(crContacts.getString(1));
         //   conNumbers.add(crContacts.getString(2));
            conCont.add(new SetContactData(crContacts.getString(1),crContacts.getString(2)));
            crContacts.moveToNext();
        }

        //	ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,R.id.tvNameMain,conNames);


        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text

                textlength = inputSearch.getText().length();

                Log.d("InputSearch",inputSearch.getText().toString());

                if(array_sort.size()!=0)
                {
                    for(int i=(array_sort.size()-1);i>=0;i--)
                    {
                        array_sort.remove(i);


                    }

                    array_sort=new ArrayList<SetContactData>();
                }


                for (int i = 0; i < conCont.size(); i++)
                {
                    if (textlength <= conCont.get(i).getName().length())
                    {

                        String s2= inputSearch.getText().toString();
                        if(conCont.get(i).getName().toString().contains(inputSearch.getText().toString()))
                        {
                            array_sort.add(conCont.get(i));


                        }


                    }
                }

                if( inputSearch.getText().toString().length()> 0) {

                    lv.setAdapter(new MyAdapter(MobileContactsActivity.this, android.R.layout.simple_list_item_1,
                            R.id.name, array_sort));
                }

                //  adapter= new CustomAdapter(dataModels,getApplicationContext());



                //        MainActivity1.this.adapter.getFilter().filter(cs);
//                adapter.notifyDataSetChanged();

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub



            }
        });

        if(inputSearch.getText().toString().length()==0 ) {

         //   count = 1;
            lv.setAdapter(new MyAdapter(MobileContactsActivity.this, android.R.layout.simple_list_item_1,
                    R.id.name, conCont));

        }










        //lv.setAdapter(arrayAdapter);

    }

    public void addContact(View v)
    {
        Intent myIntent = new Intent(MobileContactsActivity.this, AddContact.class);
        myIntent.putExtra("key", value); //Optional parameters
        MobileContactsActivity.this.startActivity(myIntent);


    }


    private class MyAdapter extends ArrayAdapter<SetContactData> {

        public MyAdapter(final Context context, int resource, int textViewResourceId,
                         final ArrayList<SetContactData> conNames) {

            super(context, resource, textViewResourceId, conNames);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    String contactSelected ;
                    String contactNumberSelected;
                    contactSelected = conNames.get(position).getName().toString();
                    contactNumberSelected = conNames.get(position).getType().toString();





                    // When clicked, show a toast with the TextView text
                    Toast.makeText(getApplicationContext(), contactNumberSelected +" "+ contactSelected,
                            Toast.LENGTH_SHORT).show();
                }
            });

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row = setList(position, parent);


            return row;


        }

        public void myClickHandler(View v,int position)
        {

            if(inputSearch.getText().toString().length()>0) {

                String message = array_sort.get(position).getName().toString();
                String messege = array_sort.get(position).getType().toString();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("Message", message);
                returnIntent.putExtra("Messege", messege);

                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
            else if(inputSearch.getText().toString().length()==0)
            {
                String message = conCont.get(position).getName().toString();
                String messege = conCont.get(position).getType().toString();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("Message", message);
                returnIntent.putExtra("Messege", messege);

                setResult(Activity.RESULT_OK, returnIntent);
                finish();

            }




            Toast.makeText(getApplicationContext(), "ButtonCLicked",
                    Toast.LENGTH_SHORT).show();
        }

        private View setList(final int position, ViewGroup parent) {
            LayoutInflater inf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = inf.inflate(R.layout.mobile_contact_list_style, parent, false);


            TextView tvName = (TextView) row.findViewById(R.id.type);
            TextView tvNumber = (TextView) row.findViewById(R.id.name);
            //ImageView image = (ImageView) row.findViewById(R.id.item_info);
            Button btn= (Button) row.findViewById(R.id.button2);

            if(inputSearch.getText().toString().length()>0) {
                tvName.setText(array_sort.get(position).getName().toString());

                tvNumber.setText(array_sort.get(position).getType().toString());
            }

            else if(inputSearch.getText().toString().length()==0)
            {
                tvName.setText(conCont.get(position).getName().toString());

                tvNumber.setText(conCont.get(position).getType().toString());

            }


            btn.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    myClickHandler(v,position);
                }
            });

            return row;
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater imf = getMenuInflater();
        imf.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item1) {
            Intent intent = new Intent(MobileContactsActivity.this, AddContact.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }}
