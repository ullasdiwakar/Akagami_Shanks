package fujitora.kaigun_taisho.staysafe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class UserPGDetails extends AppCompatActivity {

    private Toolbar toolbar;
    private ArrayList<String> urls = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pgdetails);

        toolbar = (Toolbar) findViewById(R.id.userPGDetailsToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Nico Robin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        RecyclerView list = (RecyclerView) findViewById(R.id.userPGDetailsImagePG);
        list.setLayoutManager(layoutManager);
        sampleAdapter imagesAdapter=new sampleAdapter();
        list.setAdapter(imagesAdapter);
        urls.add("https://firebasestorage.googleapis.com/v0/b/staysafe-2ab20.appspot.com/o/four.png?alt=media&token=94f8620b-2ad0-4644-8aa1-58aeb85cf785");
        urls.add("https://www.google.co.in/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwj--7-9_YXSAhWKQY8KHbgdD40QjRwIBw&url=https%3A%2F%2Fwww.pinterest.com%2Fvalgaav%2Froronoa-zoro%2F&bvm=bv.146496531,d.c2I&psig=AFQjCNHdfXKyFvDmrVA0Awlbo0HrELtunQ&ust=1486831593017875");
        urls.add("https://www.google.co.in/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwjaqM3d_YXSAhVMQo8KHYwlDRgQjRwIBw&url=http%3A%2F%2Fsergiart.deviantart.com%2Fart%2FWanted-3-Nami-562421224&bvm=bv.146496531,d.c2I&psig=AFQjCNEF1oSTwecbFoeSdfs-WSA-vOfCyg&ust=1486831654761912");
        urls.add("https://www.google.co.in/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwjw88_L_YXSAhXIpY8KHVfADl4QjRwIBw&url=http%3A%2F%2Fwww.designntrend.com%2Farticles%2F68191%2F20160112%2Fone-piece-chapter-812-how-strong-exactly-are-zoro-and-sanji.htm&bvm=bv.146496531,d.c2I&psig=AFQjCNE78EK5I9lrTLJccIBgamj2iIMJ0w&ust=1486831624813119");
        urls.add("https://www.google.co.in/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwidnffw_YXSAhUmT48KHR5yBv4QjRwIBw&url=https%3A%2F%2Fwww.themebeta.com%2Fchrome%2Ftag%2F60353&bvm=bv.146496531,d.c2I&psig=AFQjCNEomVNTTI31MamGarT7bZ1Tm0Ki5A&ust=1486831706821597");
        urls.add("https://www.google.co.in/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwjixaqN_oXSAhVLNo8KHRmkB9oQjRwIBw&url=http%3A%2F%2Fgiphy.com%2Fsearch%2Fportgas-d-ace-one-piece&bvm=bv.146496531,d.c2I&psig=AFQjCNGb3DdZBBASaVP-XtORr81iz4EoQA&ust=1486831736138752");
        urls.add("https://www.google.co.in/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwi0-6Wf_oXSAhXLL48KHaPIAj0QjRwIBw&url=http%3A%2F%2Fgiphy.com%2Fsearch%2Fshanks&bvm=bv.146496531,d.c2I&psig=AFQjCNEYXA0-1J-h4FCfKaCiAlrZwM16wg&ust=1486831799786760");



    }
    public class sampleAdapter extends RecyclerView.Adapter<sampleAdapter.ViewHolder> {

        @Override
        public sampleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=View.inflate(parent.getContext(),R.layout.user_pg_details_image_pg,null);
            ViewHolder holder=new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(sampleAdapter.ViewHolder holder, int position) {
          //  Log.v("inadapter",urls.get(position));
            Glide.with(getApplicationContext()).load(R.drawable.robin).into(holder.imageView);


        }

        @Override
        public int getItemCount() {

            return urls.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.userPGDetailsImagePG);

            }
        }
    }
}
