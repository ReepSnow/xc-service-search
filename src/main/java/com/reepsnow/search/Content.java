package com.reepsnow.search;

import java.util.List;

/**
 * @Auther: 王鹏涛 pengtao.wang@weimob.com
 * @Date: 2020/7/18 12:08
 * @Description:
 */
public class Content {

  private Long activityId;
  private Long endDate ;

  private Long pid;
  private List<Long> goodsIds;

  public Long getActivityId() {
    return activityId;
  }

  public void setActivityId(Long activityId) {
    this.activityId = activityId;
  }

  public Long getEndDate() {
    return endDate;
  }

  public void setEndDate(Long endDate) {
    this.endDate = endDate;
  }

  public Long getPid() {
    return pid;
  }

  public void setPid(Long pid) {
    this.pid = pid;
  }

  public List<Long> getGoodsIds() {
    return goodsIds;
  }

  public void setGoodsIds(List<Long> goodsIds) {
    this.goodsIds = goodsIds;
  }
}
