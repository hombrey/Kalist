package com.archbrey.Kalist;

import java.util.Comparator;


 class CalStartCompare implements Comparator<EventItem> {

    @Override
    public int compare(EventItem o1, EventItem o2) {

        int IntResult;

        if (o1.StartDate.before(o2.StartDate)) IntResult = -1;
        else if (o1.StartDate.after(o2.StartDate)) IntResult = 1;
        else IntResult = 0;

        return IntResult;
    } //public int compare(EventItem o1, EventItem o2)

} //class CalStartCompare implements Comparator<EventItem>
