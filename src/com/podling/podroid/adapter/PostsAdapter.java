package com.podling.podroid.adapter;

import java.util.List;

import org.the86.model.Post;
import org.the86.model.User;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.kevinsawicki.timeago.TimeAgo;
import com.podling.podroid.DownloadImageTask;
import com.podling.podroid.PodroidApplication;
import com.podling.podroid.R;
import com.podling.podroid.util.HtmlTextView;
import com.podling.podroid.util.PostUtil;

public class PostsAdapter extends ArrayAdapter<Post> {
	private final Activity context;
	private static final int LAYOUT = R.layout.post;

	public PostsAdapter(Activity context, List<Post> posts) {
		super(context, LAYOUT, posts);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PostViewHolder holder;

		Post post = getItem(position);
		User user = post.getUser();

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(LAYOUT, null);
			holder = new PostViewHolder();

			holder.avatar = (ImageView) convertView
					.findViewById(R.id.post_user_avatar);
			holder.posterName = (TextView) convertView
					.findViewById(R.id.post_user_name);
			holder.status = (TextView) convertView
					.findViewById(R.id.post_status);
			holder.likes = (TextView) convertView.findViewById(R.id.post_likes);
			holder.content = (HtmlTextView) convertView
					.findViewById(R.id.post_content);

			convertView.setTag(holder);
		} else {
			holder = (PostViewHolder) convertView.getTag();
		}

		holder.posterName.setText(user.getName());

		holder.status.setText(statusForPost(post));

		holder.content.setHtml(post.getContentHtml());
		holder.content.setMovementMethod(HtmlTextView.LocalLinkMovementMethod
				.getInstance());

		holder.likes.setText(PostUtil.likeCount(post));

		holder.position = position;

		if (user.getAvatarUrl() != null) {
			new DownloadImageTask(
					(PodroidApplication) context.getApplication(), position,
					holder).execute(user.getAvatarUrl());
		} else {
			holder.avatar.setImageResource(R.drawable.ic_contact_picture);
		}

		return convertView;
	}

	private String statusForPost(Post post) {
		String status = new TimeAgo().timeAgo(post.getCreatedAt());
		if (post.getInReplyToId() != null) {
			status = String.format("%s in reply to %s", status, post
					.getInReplyTo().getUser().getName());
		}
		return status;
	}

	static class PostViewHolder extends AvatarViewHolder {
		TextView posterName;
		TextView status;
		TextView likes;
		HtmlTextView content;
	}

}