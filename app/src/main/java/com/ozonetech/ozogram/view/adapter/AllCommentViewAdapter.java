package com.ozonetech.ozogram.view.adapter;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.adapters.AdapterViewBindingAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.ozonetech.ozogram.R;
import com.ozonetech.ozogram.app.utils.SessionManager;
import com.ozonetech.ozogram.base.AppBaseRecycleAdapter;
import com.ozonetech.ozogram.base.BaseRecycleAdapter;
import com.ozonetech.ozogram.customeview.ReadMoreTextView;
import com.ozonetech.ozogram.databinding.PostRowItemBinding;
import com.ozonetech.ozogram.model.CommentModel;
import com.ozonetech.ozogram.model.GetPostRecordModel;
import com.ozonetech.ozogram.model.LikeUserModel;
import com.ozonetech.ozogram.view.activity.ViewAllCommentActivity;
import com.ozonetech.ozogram.viewmodel.ShareUserListViewModel;

import java.util.List;

public class AllCommentViewAdapter extends AppBaseRecycleAdapter {
    private final ViewAllCommentActivity allCommentActivity;
    private GetPostRecordModel postObject;
    private List<CommentModel> commentList;
    ViewDataBinding bindingAdapter;
    Context context;
    private boolean is_like;
    private SessionManager session;
    private String tag = "AllCommentViewAdapter";


    public AllCommentViewAdapter(Context applicationContext, GetPostRecordModel comments, ViewAllCommentActivity viewAllCommentActivity) {
        this.allCommentActivity = viewAllCommentActivity;
        this.context = applicationContext;
        postObject = comments;
        commentList = comments.getComments();
        session = new SessionManager(context);
    }

    @Override
    public BaseViewHolder getViewHolder() {

        return new ViewHolder(inflateLayout(R.layout.view_comment_view));
    }

    @Override
    public int getDataCount() {

        return commentList == null ? 0 : commentList.size();
    }

    public int getViewHeight() {
        return 0;
    }

    public void update(GetPostRecordModel postDataObject) {
        this.postObject = postDataObject;
        is_like = false;
        commentList = postDataObject.getComments();
        notifyDataSetChanged();
    }

    public void updatePostion(int action_position, CommentModel commentModel) {
        is_like = false;
        notifyItemChanged(action_position, commentModel);
    }

    public class ViewHolder extends BaseRecycleAdapter.BaseViewHolder implements ReadMoreTextView.ReadMoreTextViewListener {
        ImageView iv_profile;
        TextView txt_user_name;
        ImageView iv_heart;
        ReadMoreTextView tv_message;
        TextView txt_time;
        TextView txt_total_like;
        TextView txt_reply;
        ProgressBar pb_image;
        LinearLayout ll_edit_layer;
        ImageView iv_user_photo;
        EditText et_comment;
        TextView txt_post;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_profile = itemView.findViewById(R.id.iv_profile);
            txt_user_name = itemView.findViewById(R.id.txt_user_name);
            iv_heart = itemView.findViewById(R.id.iv_heart);
            tv_message = itemView.findViewById(R.id.tv_message);
            txt_time = itemView.findViewById(R.id.txt_time);
            txt_total_like = itemView.findViewById(R.id.txt_total_like);
            txt_reply = itemView.findViewById(R.id.txt_reply);
            pb_image = itemView.findViewById(R.id.pb_image);
            ll_edit_layer = itemView.findViewById(R.id.ll_edit_layer);
            iv_user_photo = itemView.findViewById(R.id.iv_user_photo);
            et_comment = itemView.findViewById(R.id.et_comment);
            txt_post = itemView.findViewById(R.id.txt_post);
        }

        @Override
        public void setData(int position) {
            txt_reply.setTag(0);
            setLike(position, this);
            setComment(position, commentList.get(position), this);
            repy(position, this);
            replycomment(position, this);
        }

        private void setComment(int position, CommentModel commentModel, ViewHolder viewHolder) {
            viewHolder.txt_user_name.setText(commentModel.getFullname());
            viewHolder.txt_time.setText(commentModel.getTime());

            if (commentModel.getComment().equals("")) {
                tv_message.setText("No Comment");
            } else {
                tv_message.setTag(position);
                tv_message.setReadMoreTextViewListener(this);
                tv_message.setText(commentModel.getComment());
                tv_message.readMore = commentModel.isReadMore();
                tv_message.setText(commentModel.getComment(), TextView.BufferType.NORMAL);
            }

            allCommentActivity.loadImage(context, viewHolder.iv_profile, pb_image, commentModel.getProfile_picture(), R.drawable.ic_profile);
            allCommentActivity.loadImage(context, viewHolder.iv_user_photo, pb_image, session.getUserDetails().get(session.KEY_PROFILE_PICTURE), R.drawable.ic_profile);

        }

        private void setLike(int position, ViewHolder viewHolder) {
            if (commentList.get(position).getComment_likes_count() > 0) {
                viewHolder.txt_total_like.setText(commentList.get(position).getComment_likes_count() + " Like");
            } else {
                viewHolder.txt_total_like.setText("No Like");
            }

            List<LikeUserModel> likeUserModellist = commentList.get(position).getComment_like_users();
            is_like = false;

            for (int i = 0; i < likeUserModellist.size(); i++) {
                if (likeUserModellist.get(i).getUserId().equals(session.getUserDetails().get(session.KEY_USERID))) {
                    is_like = true;
                    break;
                } else {
                    is_like = false;
                }
            }
            if (is_like) {
                iv_heart.setBackgroundResource(R.drawable.ic_heart_fill);
                iv_heart.setTag(1);
                Log.d(tag, "---like --");
            } else {
                iv_heart.setBackgroundResource(R.drawable.ic_heart);
                iv_heart.setTag(0);
                Log.d(tag, "--no-like --");
            }


            iv_heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(tag, "---like click--");
                    if ((Integer) view.getTag() == 0) {
                        allCommentActivity.likeComment(position, commentList.get(position).getId());
                    }
                }
            });

        }

        private void repy(int position, ViewHolder viewHolder) {
            viewHolder.txt_reply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ((Integer) view.getTag() == 0) {
                        viewHolder.txt_reply.setTag(1);
                        viewHolder.ll_edit_layer.setVisibility(View.VISIBLE);
                    } else {
                        viewHolder.txt_reply.setTag(0);
                        viewHolder.ll_edit_layer.setVisibility(View.GONE);
                    }
                }
            });
        }


        private void replycomment(int position, ViewHolder viewHolder) {
            viewHolder.txt_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(tag, "----reply click--");
                    if (!viewHolder.et_comment.getText().toString().isEmpty()) {
                        allCommentActivity.replyToComment(position, commentList.get(position).getId().toString(), viewHolder.et_comment.getText().toString());
                        ll_edit_layer.setVisibility(View.GONE);
                        et_comment.setText("");
                    }

                }
            });
        }

        @Override
        public void onReadMoreChange(ReadMoreTextView textView) {
            int position = Integer.parseInt(textView.getTag().toString());
            commentList.get(position).setReadMore(textView.readMore);
            if (textView.readMore && getRecyclerView() != null) {
                getRecyclerView().scrollToPosition(position);
            }
        }
    }
}
