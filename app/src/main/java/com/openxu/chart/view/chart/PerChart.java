package com.openxu.chart.view.chart;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.openxu.chart.R;
import com.openxu.chart.util.DensityUtil;
import com.openxu.chart.util.FontUtil;
import com.openxu.chart.util.LogUtil;
import com.openxu.chart.view.anim.AngleEvaluator;
import com.openxu.chart.view.bean.ChartDataBean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * autour : openXu
 * date : 2017/10/10 10:27
 * className : PerChart
 * version : 1.0
 * description : 请添加类说明
 */
public class PerChart extends View {

    private String TAG = "PerChart";
    private int ScrWidth,ScrHeight;        //屏幕宽高
    private int backColor = Color.WHITE;   //图表背景色

    private PointF centerPoint;            //chart中心点坐标
    private int startAngle = -90;          //开始的角度
    private int chartSize;                      //图表大小
    private int chartRaidusInner;                      //圈半径
    private int raidusSize = DensityUtil.dip2px(getContext(), 25);  //进度宽度
    private int outSpace;          //图表与边界距离
    private int lineLenth = DensityUtil.dip2px(getContext(), 25);
    private int picSize ;//图片大小
    private int picRaidus;   //包裹图片的圆圈半径
    private int lineWidth = 1;     //线宽度

    /*图表绘制区域*/
    private RectF rectChart;

    //字体大小
    private int textColor = Color.parseColor("#999999");
    private int textSize = 35;
    private int textSizeBig = 75;

    private String nullStr = "无数据";
    private List<ChartDataBean> dataList;

    private int total;

    private Paint paintArc , paintSelected;
    private Paint paintLabel;
    private Paint mLinePaint;

    private int selectedIndex = -1;   //被选中的索引
    /**debug*/
    boolean debug = true;
    /**正在加载*/
    boolean isLoading = true;

    //RGB颜色数组
    //#D95F5B红   #7189E6蓝   #5AB9C7蓝1  #B096D5紫   #6BBA97绿1  #DCAA61黄   #7DAB58绿2  #DC7F68橙
/*    private final int arrColorRgb[][] = {
            {0, 0, 0},   //    UIColorFromRGB(0xD95F5B),
            {113, 137, 230},   //    UIColorFromRGB(0xD95F5B),
            {217, 95, 91},     //    UIColorFromRGB(0x7189E6),
            {90, 185, 199},    //    UIColorFromRGB(0x5AB9C7),
            {170, 150, 213},   //   UIColorFromRGB(0xB096D5),
            {107, 186, 151},   //    UIColorFromRGB(0x6BBA97),
            {91, 164, 231},    //    UIColorFromRGB(0x5BA4E7),
            {220, 170, 97},//    UIColorFromRGB(0xDCAA61),
            {125, 171, 88},//    UIColorFromRGB(0x7DAB58),
            {233, 200, 88},//    UIColorFromRGB(0xE9C858),
            {213, 150, 196},//    UIColorFromRGB(0xd596c4)
            {220, 127, 104}//    UIColorFromRGB(0xDC7F68),
    };*/

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
    public void setDebug(boolean debug) {
        this.debug = debug;
    }
    public PerChart(Context context) {
        this(context, null);
    }
    public PerChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public PerChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    public void init(Context context, AttributeSet attrs, int defStyleAttr) {
        dataList = new ArrayList<>();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        ScrHeight = dm.heightPixels;
        ScrWidth = dm.widthPixels;

        //画笔初始化
        paintArc = new Paint();
        paintArc.setAntiAlias(true);

        paintLabel = new Paint();
        paintLabel.setAntiAlias(true);

        paintSelected = new Paint();
        paintSelected.setColor(Color.LTGRAY);
        paintSelected.setStyle(Paint.Style.STROKE);//设置空心
        paintSelected.setStrokeWidth(lineWidth*5);
        paintSelected.setAntiAlias(true);

        mLinePaint = new Paint();
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setStrokeWidth(lineWidth);
        mLinePaint.setAntiAlias(true);
    }

    /**
     * 设置数据
     * @param clazz
     * @param per
     * @param name
     * @param dataList
     */
    public void setData(Class clazz, String per, String name, String color, String bitmap,List<Object> dataList){
        this.dataList.clear();
        total = 0;
        LogUtil.i(TAG, "设置数据"+dataList);
        if(dataList!=null){
            try{
                Field filedPer = clazz.getDeclaredField(per);
                Field filedName = clazz.getDeclaredField(name);
                Field filedColor = clazz.getDeclaredField(color);
                Field filedBitmap = clazz.getDeclaredField(bitmap);
                filedPer.setAccessible(true);
                filedName.setAccessible(true);
                filedColor.setAccessible(true);
                filedBitmap.setAccessible(true);
                for(Object obj : dataList){
                    String perStr = filedPer.get(obj).toString();
                    String colorStr = filedColor.get(obj).toString();
                    String bitmapStr = filedBitmap.get(obj).toString();
                    ChartDataBean bean = new ChartDataBean(Integer.parseInt(perStr), (String)filedName.get(obj) ,Integer.parseInt(colorStr),Integer.parseInt(bitmapStr));
                    this.dataList.add(bean);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            for(ChartDataBean bean : this.dataList){
                total += bean.getPer();
            }
        }
        Log.i(TAG, "玫瑰图设置数据dataList"+this.dataList);
        startDraw = false;
        if(centerPoint!=null && centerPoint.x>0){
            evaluatorData();
            startDraw = false;
            invalidate();
        }
    }

    boolean startDraw = false;
    public void setData(List<ChartDataBean> dataList){
        this.dataList.clear();
        total = 0;
        if(dataList!=null){
            this.dataList.addAll(dataList);
            for(ChartDataBean bean : this.dataList){
                total += bean.getPer();
            }
        }
        startDraw = false;
        if(centerPoint!=null && centerPoint.x>0){
            evaluatorData();
            startDraw = false;
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width, height;
        width = widthSize;
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = width;
        }
        chartSize = width>height?height:width;   //宽高中较小者
        centerPoint = new PointF(width/2, height/2);
        rectChart = new RectF((width-chartSize)/2,(height-chartSize)/2,(width+chartSize)/2, (height+chartSize)/2);
        picSize = BitmapFactory.decodeResource(getResources(), R.mipmap.value_share_select).getHeight();
        //图片半径
        picRaidus = (int)(picSize*Math.sin(45));
        chartSize = chartSize/2;
        //外侧间距应该至少等容纳一行字
        paintLabel.setTextSize(textSize);
        outSpace =(int)(FontUtil.getFontHeight(paintLabel)+10);
        chartRaidusInner = chartSize - outSpace - picRaidus*2 - lineLenth - raidusSize;
        setMeasuredDimension(width, height);
        LogUtil.i(TAG, "图表总宽高="+width+"*"+height
                +"  图表大小"+chartSize
                +"  outSpace"+outSpace
                +"  picSize"+picSize
                +"  picRaidus"+picRaidus
                +"  lineLenth"+lineLenth
                +"  raidusSize"+raidusSize
                +"  chartRaidusInner"+chartRaidusInner);
        if(dataList!=null && dataList.size()>0){
            evaluatorData();
            startDraw = false;
            invalidate();
        }
    }

    /**计算各种绘制坐标*/
    private void evaluatorData(){

        paintLabel.setTextSize(textSize);
        float textLead = FontUtil.getFontLeading(paintLabel);
        float textHeight = FontUtil.getFontHeight(paintLabel);

        float radius = chartRaidusInner + raidusSize; //每部分扇形半径
        float oneStartAngle = startAngle;
        for(int i = 0; i < dataList.size(); i++) {
            ChartDataBean bean = dataList.get(i);
            /**1、绘制扇形*/
            float arcLeft = centerPoint.x - radius;
            float arcTop = centerPoint.y - radius;
            float arcRight = centerPoint.x + radius;
            float arcBottom = centerPoint.y + radius;

            float percentage = 360.0f / total * bean.getPer();
            bean.setArcRect(new RectF(arcLeft, arcTop, arcRight, arcBottom));
            bean.setStartAngle(oneStartAngle);
            bean.setSweepAngle(percentage);

            /**2、计算扇形区域*/
            arcLeft = centerPoint.x - chartSize;
            arcTop = centerPoint.y - chartSize;
            arcRight = centerPoint.x + chartSize;
            arcBottom = centerPoint.y + chartSize;
            Path allPath = new Path();
            allPath.moveTo(centerPoint.x, centerPoint.y);//添加原始点
            float ovalX = centerPoint.x + (float) (radius * Math.cos(Math.toRadians(oneStartAngle)));
            float ovalY = centerPoint.y + (float) (radius * Math.sin(Math.toRadians(oneStartAngle)));
            allPath.lineTo(ovalX, ovalY);
            RectF touchOval = new RectF(arcLeft, arcTop, arcRight, arcBottom);
            allPath.addArc(touchOval, oneStartAngle, percentage);
            allPath.lineTo(centerPoint.x, centerPoint.y);
            allPath.close();
            RectF r = new RectF();
            allPath.computeBounds(r, true);
            Region region = new Region();
            region.setPath(allPath, new Region((int) r.left, (int) r.top, (int) r.right, (int) r.bottom));
            bean.setRegion(region);

            /**3、绘制直线*/
            //确定直线的起始和结束的点的位置
            float startX = centerPoint.x + (float) (radius * Math.cos(Math.toRadians(oneStartAngle + percentage / 2)));
            float startY = centerPoint.y + (float) (radius * Math.sin(Math.toRadians(oneStartAngle + percentage / 2)));
            float endX = centerPoint.x + (float) ((radius + lineLenth) * Math.cos(Math.toRadians(oneStartAngle + percentage / 2)));
            float endY = centerPoint.y + (float) ((radius + lineLenth) * Math.sin(Math.toRadians(oneStartAngle + percentage / 2)));

            List<PointF> tagLinePoints = new ArrayList<>();
            tagLinePoints.add(new PointF(startX, startY));
            tagLinePoints.add(new PointF(endX, endY));
            bean.setTagLinePoints(tagLinePoints);

            /**4、图片*/
            float picCenterX = centerPoint.x + (float) ((radius + lineLenth + picRaidus) * Math.cos(Math.toRadians(oneStartAngle + percentage / 2)));
            float picCenterY = centerPoint.y + (float) ((radius + lineLenth + picRaidus) * Math.sin(Math.toRadians(oneStartAngle + percentage / 2)));
            PointF picPoint = new PointF(picCenterX-picSize/2, picCenterY - picSize/2);
            PointF picCenterPoint = new PointF(picCenterX, picCenterY);
            bean.setPicPoint(picPoint);
            bean.setPicCenterPoint(picCenterPoint);

            /**5、计算文字绘制坐标*/
            paintLabel.setTextSize(textSize);
            int prePx, prePy, namePx, namePy;
            //标签字体长度
            int pre = bean.getPer() *100 /total;
            bean.setPerStr(pre+"%");
            float textW = FontUtil.getFontlength(paintLabel, bean.getPerStr());
            float lineAngle = oneStartAngle + percentage / 2;
            LogUtil.i(TAG,"角度="+lineAngle +"  textW="+textW );
            if (lineAngle >45 && lineAngle <= 90) {
                prePx = (int)(picCenterX);
            }else if(lineAngle > 90 && lineAngle <= 135){
                prePx = (int)(picCenterX - textW);
            }else{
                prePx = (int)(picCenterX - textW/2);
            }
            prePy =(int)(picCenterY-picRaidus-textHeight+textLead-10);
            bean.setPrePoint(new PointF(prePx, prePy));

            textW = FontUtil.getFontlength(paintLabel, bean.getName());
            LogUtil.i(TAG,"角度="+lineAngle +"  textW="+textW );
            if(lineAngle>=-90&& lineAngle <= -45){
                namePx = (int)(endX +10);
            }else if(lineAngle>=-45&& lineAngle <= 225){
                namePx = (int)(picCenterX - textW/2);
            }else {
                namePx = (int)(endX - textW-10);
            }
            namePy =(int)(picCenterY+picRaidus+textLead+10);
            bean.setNamePoint(new PointF(namePx, namePy));

            /*开始角度累加*/
            oneStartAngle += percentage;
        }

    }

    public void onDraw(Canvas canvas){
        //画布背景
        canvas.drawColor(backColor);

        /*正在加载数据中*/
        if(isLoading){
            paintLabel.setTextSize(35);
            float NullTextLead = FontUtil.getFontLeading(paintLabel);
            float NullTextHeight = FontUtil.getFontHeight(paintLabel);
            float textY = centerPoint.y-NullTextHeight/2+NullTextLead;
            paintLabel.setColor(Color.GRAY);
            canvas.drawText("loading...", centerPoint.x-FontUtil.getFontlength(paintLabel, "loading...")/2,  textY, paintLabel);
            return;
        }
        /*未获取到数据*/
        if(dataList==null || dataList.size()<=0) {
            float NullTextLead = FontUtil.getFontLeading(paintLabel);
            float NullTextHeight = FontUtil.getFontHeight(paintLabel);
            float textY = centerPoint.y-NullTextHeight/2+NullTextLead;
            canvas.drawText(nullStr, centerPoint.x-FontUtil.getFontlength(paintLabel, nullStr)/2,  textY, paintLabel);
            return;
        }

        if(debug) {
            paintArc.setStyle(Paint.Style.STROKE);//设置空心
            //绘制边界--chart区域
            paintArc.setColor(Color.YELLOW);
            canvas.drawRect(rectChart, paintArc);
            //绘制边界--chart圆边界
            paintArc.setColor(Color.BLUE);
            canvas.drawCircle(centerPoint.x, centerPoint.y, chartRaidusInner + outSpace + picRaidus * 2 + lineLenth + raidusSize, paintArc);
            canvas.drawCircle(centerPoint.x, centerPoint.y, chartRaidusInner + picRaidus * 2 + lineLenth + raidusSize, paintArc);
            canvas.drawCircle(centerPoint.x, centerPoint.y, chartRaidusInner + lineLenth + raidusSize, paintArc);
            canvas.drawCircle(centerPoint.x, centerPoint.y, chartRaidusInner + raidusSize, paintArc);
            canvas.drawCircle(centerPoint.x, centerPoint.y, chartRaidusInner, paintArc);
        }
        if(!startDraw){
            startDraw = true;
            startAnimation();
        }else{
            drawChart(canvas);
        }
    }


    private long duration = 1000;
    ValueAnimator anim;
    private boolean animFinish = false;
    private void startAnimation() {
        if(anim!=null){
            anim.cancel();
        }
        Log.w(TAG, "开始动画");
        //将百分比转换为扇形半径长度
        anim = ValueAnimator.ofObject(new AngleEvaluator(), 0f, 1.0f);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float percentage = (float) animation.getAnimatedValue();
                evaluatorAnimData(percentage);
                invalidate();
            }
        });
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                animFinish = false;
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                animFinish = true;
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        anim.setDuration(duration);
        anim.start();
    }

    /**
     * 计算各种绘制坐标
     * @param percent 当前扇形度0-1
     */
    private void evaluatorAnimData(float percent){
        float radius = chartRaidusInner + raidusSize; //每部分扇形半径
        float oneStartAngle = startAngle;
        for(int i = 0; i < dataList.size(); i++) {
            ChartDataBean bean = dataList.get(i);
            /**1、绘制扇形*/
            float arcLeft = centerPoint.x - radius;
            float arcTop = centerPoint.y - radius;
            float arcRight = centerPoint.x + radius;
            float arcBottom = centerPoint.y + radius;

            float percentage = (360.0f / total * bean.getPer())*percent;
            bean.setArcRect(new RectF(arcLeft, arcTop, arcRight, arcBottom));
            bean.setStartAngle(oneStartAngle);
            bean.setSweepAngle(percentage);

            /*开始角度累加*/
            oneStartAngle += percentage;
        }

    }
    private void drawChart(Canvas canvas){
        for(int i = 0; i < dataList.size(); i++){

            ChartDataBean bean = dataList.get(i);

            paintArc.setColor(bean.getColor());
            mLinePaint.setColor(bean.getColor());
//            paintArc.setARGB(255, arrColorRgb[i%arrColorRgb.length][0], arrColorRgb[i%arrColorRgb.length][1], arrColorRgb[i%arrColorRgb.length][2]);
            if(selectedIndex == i){
                paintLabel.setTypeface(Typeface.DEFAULT_BOLD);
                mLinePaint.setTypeface(Typeface.DEFAULT_BOLD);
            }else{
                paintLabel.setTypeface(Typeface.DEFAULT);
                mLinePaint.setTypeface(Typeface.DEFAULT);
            }
            paintLabel.setTextSize(textSizeBig);

            /**1、绘制扇形*/
            paintArc.setStyle(Paint.Style.FILL);//设置实心
            canvas.drawArc(bean.getArcRect(), bean.getStartAngle(), bean.getSweepAngle(), true, paintArc);
            if(selectedIndex == i){
                //被选中的，绘制边界
                paintSelected.setStyle(Paint.Style.STROKE);//设置空心
                canvas.drawArc(bean.getArcRect(), bean.getStartAngle(), bean.getSweepAngle(), true, paintSelected);
            }
            if(animFinish){
                /**2、绘制直线*/
                List<PointF> tagLinePoints = bean.getTagLinePoints();
                if (tagLinePoints != null && tagLinePoints.size() > 0) {
                    for (int p = 1; p < tagLinePoints.size(); p++) {
                        canvas.drawLine(tagLinePoints.get(p - 1).x, tagLinePoints.get(p - 1).y,
                                tagLinePoints.get(p).x, tagLinePoints.get(p).y, mLinePaint);
                    }
                }
                /**3、绘制图片*/
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),bean.getBitmapID()), bean.getPicPoint().x, bean.getPicPoint().y, paintLabel);
                paintArc.setStyle(Paint.Style.STROKE);
                canvas.drawCircle(bean.getPicCenterPoint().x, bean.getPicCenterPoint().y, picRaidus, paintArc);
                /**4、绘制指示标签*/
                paintLabel.setColor(textColor);
                paintLabel.setTextSize(textSize);
                canvas.drawText(bean.getPerStr(), bean.getPrePoint().x, bean.getPrePoint().y, paintLabel);
                canvas.drawText(bean.getName(), bean.getNamePoint().x, bean.getNamePoint().y, paintLabel);
            }
        }

        //绘制中心内圆
        paintArc.setColor(backColor);
        paintArc.setStyle(Paint.Style.FILL);//设置实心
        canvas.drawCircle(centerPoint.x, centerPoint.y, chartRaidusInner, paintArc);
        /*中间的文字*/
        float allHeight = 0;
        paintLabel.setTextSize(textSizeBig);
        allHeight += FontUtil.getFontHeight(paintLabel);
        paintLabel.setTextSize(textSize);
        allHeight += FontUtil.getFontHeight(paintLabel);;

        paintLabel.setColor(Color.BLACK);
        paintLabel.setTextSize(textSizeBig);
        float textW = FontUtil.getFontlength(paintLabel, total+"");
        float textH = FontUtil.getFontHeight(paintLabel);
        canvas.drawText(total+"", centerPoint.x-textW/2, centerPoint.y-allHeight/2+FontUtil.getFontLeading(paintLabel), paintLabel);
        paintLabel.setColor(textColor);
        paintLabel.setTextSize(textSize);
        textH = FontUtil.getFontHeight(paintLabel);
        textW = FontUtil.getFontlength(paintLabel, "总计");
        canvas.drawText("总计", centerPoint.x-textW/2,
                centerPoint.y+DensityUtil.dip2px(getContext(), 5)+FontUtil.getFontLeading(paintLabel), paintLabel);
    }



}
