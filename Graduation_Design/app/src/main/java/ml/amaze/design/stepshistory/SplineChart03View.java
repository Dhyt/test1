package ml.amaze.design.stepshistory;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import org.xclcharts.chart.CustomLineData;
import org.xclcharts.chart.PointD;
import org.xclcharts.chart.SplineChart;
import org.xclcharts.chart.SplineData;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.event.click.PointPosition;
import org.xclcharts.renderer.XEnum;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author hxj
 */
public class SplineChart03View extends DemoView {
	

	private String tag = "SplineChart03View";
	private SplineChart chart = new SplineChart();
	//分类轴标签集合

	private  LinkedList<String> labels = new LinkedList<>();
	private LinkedList<SplineData> chartData= new LinkedList<>();
	
	private Paint mPaintTooltips = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	//setCategoryAxisCustomLines
	// splinechart支持横向和竖向定制线

	private List<CustomLineData> mXCustomLineDataset = new ArrayList<CustomLineData>();
	private List<CustomLineData> mYCustomLineDataset = new ArrayList<CustomLineData>();
	
	public SplineChart03View(Context context,LinkedList<String> labels,LinkedList<SplineData> chartData) {
		super(context);
		// TODO Auto-generated constructor stub
		this.labels=labels;
		this.chartData=chartData;
		initView();

	}
	
	public SplineChart03View(Context context, AttributeSet attrs){   
	    super(context, attrs);   
	    initView();
	 }
	 
	 public SplineChart03View(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			initView();
	 }
	 
	 private void initView()
	 {
			//chartLabels();//横坐标
			//chartDataSet();	//数据源
			chartRender();
		 System.out.println("spline03创建initView"+labels+chartData);
			//綁定手势滑动事件
			this.bindTouch(this,chart);
	 }
	 
	 
	@Override  
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
	    super.onSizeChanged(w, h, oldw, oldh);  
	   //图所占范围大小
	    chart.setChartRange(w,h);
	}  				
	
	
	private void chartRender()
	{
		try {
						
			//设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....		
			int [] ltrb = getBarLnDefaultSpadding();
			chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);
			//chart.setPadding(0,0,0,0);
			//显示边框
			chart.showRoundBorder();
			
			//数据源	
			chart.setCategories(labels);
			chart.setDataSource(chartData);
			
						
			//坐标系
			//数据轴最大值（纵轴）
			chart.getDataAxis().setAxisMax(15000);
			//chart.getDataAxis().setAxisMin(0);
			//数据轴刻度间隔
			chart.getDataAxis().setAxisSteps(1000);
			//y轴
			chart.setCustomLines(mYCustomLineDataset);
			
			//标签轴最大值（横轴）
			chart.setCategoryAxisMax(100);
			//标签轴最小值
			chart.setCategoryAxisMin(0);
			//chart.setCustomLines(mXCustomLineDataset); //y轴
			// x轴
			chart.setCategoryAxisCustomLines(mXCustomLineDataset);
			
			//设置图的背景色
			chart.setApplyBackgroundColor(true);
			chart.setBackgroundColor( Color.rgb(212, 194, 129) );
			chart.getBorder().setBorderLineColor(Color.rgb(179, 147, 197));
					
			//调轴线与网络线风格
			chart.getCategoryAxis().hideTickMarks();
			chart.getDataAxis().hideAxisLine();
			chart.getDataAxis().hideTickMarks();		
			chart.getPlotGrid().showHorizontalLines();
			//chart.hideTopAxis();
			//chart.hideRightAxis();				
			
			chart.getPlotGrid().getHorizontalLinePaint().setColor(Color.rgb(179, 147, 197));
			chart.getCategoryAxis().getAxisPaint().setColor( 
						chart.getPlotGrid().getHorizontalLinePaint().getColor());
			chart.getCategoryAxis().getAxisPaint().setStrokeWidth(
					chart.getPlotGrid().getHorizontalLinePaint().getStrokeWidth());
			
				
			//定义交叉点标签显示格式,特别备注,因曲线图的特殊性，所以返回格式为:  x值,y值
			//请自行分析定制
			chart.setDotLabelFormatter(new IFormatterTextCallBack(){
	
				@Override
				public String textFormatter(String value) {
					// TODO Auto-generated method stub
					//values=10,40.0

					return (value.substring(value.lastIndexOf(",")+1,value.lastIndexOf(".")));
				}
				
			});
			//标题
			chart.setTitle("步数历史");
			chart.addSubtitle("(取近10天数据显示)");
			
			//激活点击监听
			chart.ActiveListenItemClick();
			//为了让触发更灵敏，可以扩大5px的点击监听范围
			chart.extPointClickRange(5);
			chart.showClikedFocus();
			
			//显示平滑曲线
			chart.setCrurveLineStyle(XEnum.CrurveLineStyle.BEZIERCURVE);
			
			//图例显示在正下方
			chart.getPlotLegend().setVerticalAlign(XEnum.VerticalAlign.BOTTOM);
			chart.getPlotLegend().setHorizontalAlign(XEnum.HorizontalAlign.CENTER);
												
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(tag, e.toString());
		}
	}

	
//	/**
//	 * 期望线/分界线
//	 */
//	private void chartCustomeLines()
//	{
//		CustomLineData cdx1 =new CustomLineData("稍好",30d,Color.rgb(35, 172, 57),5);
//		CustomLineData cdx2 =new CustomLineData("舒适",40d,Color.rgb(69, 181, 248),5);
//		cdx1.setLabelVerticalAlign(XEnum.VerticalAlign.MIDDLE);
//		mXCustomLineDataset.add(cdx1);
//		mXCustomLineDataset.add(cdx2);
//
//
//		CustomLineData cdy1 = new CustomLineData("定制线",45d,Color.rgb(69, 181, 248),5);
//		cdy1.setLabelHorizontalPostion(Align.CENTER);
//		mYCustomLineDataset.add(cdy1);
//	}
	
	
	@Override
	public void render(Canvas canvas) {
	    try{
	        chart.render(canvas);
	    } catch (Exception e){
	    	Log.e(tag, e.toString());
	    }
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub		
		
		super.onTouchEvent(event);
				
		if(event.getAction() == MotionEvent.ACTION_UP) 
		{			
			triggerClick(event.getX(),event.getY());	
		}
		return true;
	}

	/**
	 * 触发监听
	 * @param x
	 * @param y
	 */
	private void triggerClick(float x,float y)
	{
		if(!chart.getListenItemClickStatus()) {
			return;
		}
		
		PointPosition record = chart.getPositionRecord(x,y);			
		if( null == record) {
			return;
		}
	
		if(record.getDataID() >= chartData.size()){
			return;
		}
		SplineData lData = chartData.get(record.getDataID());
		List<PointD> linePoint =  lData.getLineDataSet();	
		int pos = record.getDataChildID();
		int i = 0;
		Iterator it = linePoint.iterator();
		while(it.hasNext())
		{
			PointD  entry=(PointD)it.next();

			if(pos == i)
			{
				Double xValue = entry.x;
				Double yValue = entry.y;

			     	float r = record.getRadius();
					chart.showFocusPointF(record.getPosition(),r + r*0.8f);
					chart.getFocusPaint().setStyle(Style.FILL);
					chart.getFocusPaint().setStrokeWidth(3);
					if(record.getDataID() >= 2)
					{
						chart.getFocusPaint().setColor(Color.BLUE);
					}else{
						chart.getFocusPaint().setColor(Color.RED);
					}
			     //在点击处显示tooltip
					mPaintTooltips.setColor(Color.RED);
					chart.getToolTip().setCurrentXY(x,y);
					chart.getToolTip().addToolTip(" Key:"+lData.getLineKey(),mPaintTooltips);
					chart.getToolTip().addToolTip(
							" Current Value:" +Double.toString(xValue)+","+Double.toString(yValue),mPaintTooltips);
					chart.getToolTip().getBackgroundPaint().setAlpha(100);
					this.invalidate();

			     break;
			}
	        i++;
		}//end while
				
	}

}
