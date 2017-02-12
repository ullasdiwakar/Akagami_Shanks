package fujitora.kaigun_taisho.staysafe;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;

public class DisplayPgDetails extends AppCompatActivity {
    DatabaseReference ref;
    ArrayList<String> sharingIconLink;
    ArrayList<String> facilitiesList;
    RecyclerView facilitiesRecyclerList;
    private Toolbar toolbar;
    FeatureCoverFlow mCoverFlow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharingIconLink=new ArrayList<String>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_pg_details);
        toolbar = (Toolbar) findViewById(R.id.userPGDetailsToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("PG Name");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //final RecyclerView recycler = (RecyclerView) findViewById(R.id.recyclerView);
        //recycler.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        //layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //recycler.setLayoutManager(layoutManager);
        final BannerSlider bannerSlider = (BannerSlider) findViewById(R.id.photoSlider);
        facilitiesList=new ArrayList<String>();
        facilitiesRecyclerList=(RecyclerView)findViewById(R.id.facilitiesRecyclersList);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        facilitiesRecyclerList.setLayoutManager(layoutManager);
        ref = FirebaseDatabase.getInstance().getReference("Staysafe");
        ref.child("pgDetails").child("images").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren())
                {
                    Log.v("Infirebase","  "+noteDataSnapshot.getValue(String.class)+"\n");
                    bannerSlider.addBanner(new RemoteBanner(noteDataSnapshot.getValue(String.class)));

                }


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


        ref.child("pgDetails").child("facilities").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren())
                {
                    if(noteDataSnapshot.getValue(Long.class)==1)
                    {
                       facilitiesList.add(noteDataSnapshot.getKey());
                        Log.v("In facilities",noteDataSnapshot.getKey()+"\n");
                    }
                    facilitiesRecyclerListAdapter adapter=new facilitiesRecyclerListAdapter();
                    facilitiesRecyclerList.setAdapter(adapter);

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });



        DatabaseReference mRef=ref.child("pgDetails");
        DatabaseReference mRef1=mRef.child("sharing");
        cardArrayAdapter adapter=new cardArrayAdapter(getApplicationContext(),R.layout.bed_details_card);
        mCoverFlow = (FeatureCoverFlow) findViewById(R.id.coverflow);
        mCoverFlow.setAdapter(adapter);

        // mRef1.limitToLast(4).addValueEventListener(new ValueEventListener() {
      //     @Override
      //     public void onDataChange(DataSnapshot snapshot) {
      //         for (DataSnapshot msgSnapshot: snapshot.getChildren()) {
      //             Log.v("Infirebase"," "+snapshot.getChildrenCount());
      //             pgSharingDeatils temp= msgSnapshot.getValue(pgSharingDeatils.class);
      //             //String rate=msgSnapshot.getValue(String.class);
      //             Log.v("Infirebase","Rate="+temp.getRate()+"  Available="+temp.getAvailable());
      //             //Log.v("Infirebase","Rate="+rate);
      //         }
      //     }
      //     @Override
      //     public void onCancelled(DatabaseError firebaseError) {
      //         Log.v("Chat", "The read failed: ");
      //     }
      // });
        //Log.v("Infirebase"," "+mRef1.child("1"));
       // final FirebaseRecyclerAdapter sharingAdapter = new FirebaseRecyclerAdapter<pgSharingDeatils,viewHolder>(pgSharingDeatils.class,R.layout.bed_details_card,viewHolder.class,mRef1.getRef()) {
       //     @Override
       //     protected void populateViewHolder(viewHolder viewHolder, pgSharingDeatils model, int position) {
       //         Log.v("Infirebase","enterig shaata");
       //         Log.v("Infirebase",model.available+" xvb "+model.rate);
       //         viewHolder.bedDetailsCardAvailabilityText.setText(String.valueOf(model.getAvailable()));
       //         viewHolder.bedDetailsCardCostText.setText(String.valueOf(model.getRate()));
       //         Glide.with(getApplicationContext())
       //                 .load("https://firebasestorage.googleapis.com/v0/b/staysafe-2ab20.appspot.com/o/double.png?alt=media&token=64a98118-ccd2-4587-924a-9023a20ba861")
       //                 .centerCrop()
       //                 .into(viewHolder.bedDetailsCardImage);
//
       //     }
//
//
       // };
       // sharingAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver(){
       //     @Override
       //     public void onItemRangeInserted(int positionStart, int itemCount) {
       //         super.onItemRangeInserted(positionStart, itemCount);
       //         int count = sharingAdapter.getItemCount();
       //         int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
       //         if (lastVisiblePosition == -1 ||
       //                 (positionStart >= (count - 1) &&
       //                         lastVisiblePosition == (positionStart - 1))) {
       //             recycler.scrollToPosition(positionStart);
//
       //         }
       //     }
       // });
//
       // recycler.setAdapter(sharingAdapter);
//

    }
    public static class pgSharingDeatils{
        Long rate;
        Long available;
        public pgSharingDeatils(){

        }

        public Long getRate() {
            return rate;
        }

        public Long getAvailable() {
            return available;
        }


        public pgSharingDeatils(Long available, Long rate) {
            this.available = available;
            this.rate = rate;
        }


    }
    public static class viewHolder extends RecyclerView.ViewHolder {
        private final TextView bedDetailsCardCostText;
        private final TextView  bedDetailsCardAvailabilityText;
        private final ImageView bedDetailsCardImage;
        //private final CardView bedDetailsCard;

        public viewHolder(View itemView) {
            super(itemView);
            bedDetailsCardCostText = (TextView) itemView.findViewById(R.id.bedDetailsCardCostText);
            bedDetailsCardAvailabilityText = (TextView) itemView.findViewById(R.id.bedDetailsCardAvailabilityText);
            bedDetailsCardImage=(ImageView)itemView.findViewById(R.id.bedDetailsCardImage);
            //bedDetailsCard =(CardView)itemView.findViewById(R.id.bedDetailsCard);
        }


    }
    public  class facilitiesRecyclerListAdapter extends RecyclerView.Adapter<facilitiesRecyclerListAdapter.myViewHolder>
    {
        @Override
        public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_pg_details_image_features,parent,false);
            return new myViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(myViewHolder holder, int position) {
            holder.userPGDetailsFeaturesCardName.setText(facilitiesList.get(position));
            //String s="R.drawable."+facilitiesList.get(position);
            int id = getResources().getIdentifier(facilitiesList.get(position), "drawable", getApplicationContext().getPackageName());
            Log.v("Infirebase"," "+id+" "+facilitiesList.get(position)+".png");
            Glide.with(getApplicationContext())
                    .load(id)
                    .into(holder.userPGDetailsFeaturesCardImage);

        }

        @Override
        public int getItemCount() {
            return facilitiesList.size();
        }

        public class myViewHolder extends RecyclerView.ViewHolder {
            CardView userPGDetailsFeaturesCard;
            TextView userPGDetailsFeaturesCardName;
            ImageView userPGDetailsFeaturesCardImage;;
            public myViewHolder(View itemView) {
                super(itemView);
                userPGDetailsFeaturesCard=(CardView)itemView.findViewById(R.id.userPGDetailsFeaturesCard);
                userPGDetailsFeaturesCardImage=(ImageView)itemView.findViewById(R.id.userPGDetailsFeaturesCardImage);
                userPGDetailsFeaturesCardName=(TextView)itemView.findViewById(R.id.userPGDetailsFeaturesCardName);
            }
        }
        }
    public class cardArrayAdapter extends ArrayAdapter {
        List<CardView> cardlist= new ArrayList<>();
        class cardViewHolder {
            TextView bedDetailsCardCostText;
            TextView bedDetailsCardAvailabilityText;
            ImageView bedDetailsCardImage;
            CardView bedDetailsCard;
        }

        public cardArrayAdapter(Context context, int resource) {
            super(context, resource);
        }


        @Override
        public int getCount() {
            return 5;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.bed_details_card,parent,false);
            cardArrayAdapter.cardViewHolder holder=new cardArrayAdapter.cardViewHolder();
            holder.bedDetailsCardAvailabilityText=(TextView)view.findViewById(R.id.bedDetailsCardAvailabilityText);
            holder.bedDetailsCardCostText=(TextView)view.findViewById(R.id.bedDetailsCardCostText);
            holder.bedDetailsCardImage=(ImageView)view.findViewById(R.id.bedDetailsCardImage);
            holder.bedDetailsCard=(CardView)view.findViewById(R.id.bedDetailsCard);
            holder.bedDetailsCardCostText.setText("Cost:\n1000");
            holder.bedDetailsCardImage.setImageResource(R.drawable.nogut);
            holder.bedDetailsCardAvailabilityText.setText("Availability:\n29");
            return view;

        }
    }



}

