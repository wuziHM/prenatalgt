package com.yunkang.prenatalgt.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yunkang.prenatalgt.R;

import java.util.ArrayList;

/**
 * WH1604073
 * 群头像控件
 *
 * @author user
 */
public class DeformablePhotoView extends RelativeLayout {

    private View contentView;
    private ImageView groupPhotoImage_1;
    private ImageView groupPhotoImage_2;
    private ImageView groupPhotoImage_3;
    private ImageView groupPhotoImage_4;
    private RelativeLayout circleRl;
    private LinearLayout groupImgRt_1;
    private LinearLayout groupImgRt_2;

    public DeformablePhotoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public DeformablePhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        contentView = inflate(context, R.layout.deformable_photo_view, this);

        groupPhotoImage_1 = contentView
                .findViewById(R.id.img_head_1);
        groupPhotoImage_2 = contentView
                .findViewById(R.id.img_head_2);
        groupPhotoImage_3 = contentView
                .findViewById(R.id.img_head_3);
        groupPhotoImage_4 = contentView
                .findViewById(R.id.img_head_4);

        circleRl = contentView.findViewById(R.id.rl_circle);

        groupImgRt_1 = contentView.findViewById(R.id.img_rt_1);
        groupImgRt_2 = contentView.findViewById(R.id.img_rt_2);

    }

    public DeformablePhotoView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    /**
     * 处理群组头像显示
     *
     * @param imgUrls
     */
    public void photoViewProcess(String imgUrls) {
        // 是群组 则查询组合群组头像所需的url
        ArrayList<ImageView> imageViewList = new ArrayList<ImageView>();
        String[] urls = null;
        int length = 0;
        if (!TextUtils.isEmpty(imgUrls)) {
            urls = TextUtils.split(imgUrls, "=");
            length = urls.length;
        }

        if (length == 4) {
            imageViewList.add(groupPhotoImage_1);
            imageViewList.add(groupPhotoImage_2);
            imageViewList.add(groupPhotoImage_3);
            imageViewList.add(groupPhotoImage_4);
            groupPhotoImage_1.setVisibility(View.VISIBLE);
            groupPhotoImage_2.setVisibility(View.VISIBLE);
            groupPhotoImage_3.setVisibility(View.VISIBLE);
            groupPhotoImage_4.setVisibility(View.VISIBLE);
            groupImgRt_1.setVisibility(View.VISIBLE);
            groupImgRt_2.setVisibility(View.VISIBLE);
        } else if (length == 3) {
            groupPhotoImage_1.setVisibility(View.VISIBLE);
            groupPhotoImage_2.setVisibility(View.GONE);
            groupPhotoImage_3.setVisibility(View.VISIBLE);
            groupPhotoImage_4.setVisibility(View.VISIBLE);
            groupImgRt_1.setVisibility(View.VISIBLE);
            groupImgRt_2.setVisibility(View.VISIBLE);

            imageViewList.add(groupPhotoImage_1);
            imageViewList.add(groupPhotoImage_3);
            imageViewList.add(groupPhotoImage_4);
        } else if (length == 2) {
            groupPhotoImage_1.setVisibility(View.GONE);
            groupPhotoImage_2.setVisibility(View.GONE);
            groupPhotoImage_3.setVisibility(View.VISIBLE);
            groupPhotoImage_4.setVisibility(View.VISIBLE);
            groupImgRt_1.setVisibility(View.GONE);
            groupImgRt_2.setVisibility(View.VISIBLE);
            imageViewList.add(groupPhotoImage_3);
            imageViewList.add(groupPhotoImage_4);
        } else if (length == 1) {
            groupPhotoImage_1.setVisibility(View.VISIBLE);
            groupPhotoImage_2.setVisibility(View.GONE);
            groupPhotoImage_3.setVisibility(View.GONE);
            groupPhotoImage_4.setVisibility(View.GONE);
            groupImgRt_1.setVisibility(View.VISIBLE);
            groupImgRt_2.setVisibility(View.GONE);
            imageViewList.add(groupPhotoImage_1);
        } else if (length == 0) {
            groupPhotoImage_1.setVisibility(View.GONE);
            groupPhotoImage_2.setVisibility(View.GONE);
            groupPhotoImage_3.setVisibility(View.GONE);
            groupPhotoImage_4.setVisibility(View.GONE);
            groupImgRt_1.setVisibility(View.GONE);
            groupImgRt_2.setVisibility(View.GONE);
        }

        // 群成像生成规则
        settingImgHead(imageViewList, urls, length);
    }

    /**
     * 群头像生成
     */
    private void settingImgHead(ArrayList<ImageView> imageViewList,
                                String[] urls, int length) {
//		if (imageViewList.size() > 0 && length > 0
//				&& imageViewList.size() == length) {
//			circleRl.setBackgroundResource(0);
//			for (int i = 0; i < imageViewList.size(); i++) {
//				ImageView iv = imageViewList.get(i);
//				String url = urls[i];
////				GroupOperation.getInstance().loadGroupPhoto(url, iv);
////				ImageLoadUtils.loadRoundPhoto(url, iv);
//			}
//		} else {
//			circleRl.setBackgroundResource(R.mipmap.icon_group_mr);
//		}
        circleRl.setBackgroundResource(R.mipmap.icon_group_mr);
    }
}
