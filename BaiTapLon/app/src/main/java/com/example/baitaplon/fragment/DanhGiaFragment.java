    package com.example.baitaplon.fragment;

    import android.os.Bundle;

    import androidx.fragment.app.Fragment;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.RatingBar;
    import android.widget.Toast;

    import com.example.baitaplon.Details;
    import com.example.baitaplon.R;
    import com.example.baitaplon.database.Comment;
    import com.example.baitaplon.database.CommentDataSource;

    import java.time.LocalDate;


    public class DanhGiaFragment extends Fragment {
        View mview;
        Button btnSubmit;
        RatingBar ratingBar;

        Details activityDetail;
        CommentDataSource commentDataSource;
        Comment comment;
        private int id_user_detail;
        private int id_quan_detail;
        double rating;



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            mview = inflater.inflate(R.layout.fragment_danh_gia, container, false);
            btnSubmit = mview.findViewById(R.id.btnSubmit);
            ratingBar = mview.findViewById(R.id.ratingBar);
            activityDetail = (Details) getActivity();

            id_user_detail = activityDetail.getId_user_detail();
            id_quan_detail = activityDetail.getId_quan_detail();


            commentDataSource = new CommentDataSource(requireContext());
            commentDataSource.open();
            rating = commentDataSource.getRatingFromComment(id_user_detail,id_quan_detail);
            ratingBar.setRating( (float) rating);
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    commentDataSource.open();
                    if(!commentDataSource.checkCommentExists(id_user_detail,id_quan_detail)){
                        commentDataSource.insertComment("",(float) ratingBar.getRating(),String.valueOf(LocalDate.now()),id_user_detail,id_quan_detail);
                        Toast.makeText(activityDetail, "Đánh giá thành công new", Toast.LENGTH_SHORT).show();
                    }else{
                        rating = ratingBar.getRating();
                        try {
                            commentDataSource.updateRating(id_user_detail,id_quan_detail,(double) rating);
                            Toast.makeText(activityDetail, "Đánh giá thành công ", Toast.LENGTH_SHORT).show();
                        }catch (Exception ex){
                            Toast.makeText(activityDetail, "Đánh giá thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            });
            initUI();

            return mview;
        }

        private void initUI() {




        }
    }