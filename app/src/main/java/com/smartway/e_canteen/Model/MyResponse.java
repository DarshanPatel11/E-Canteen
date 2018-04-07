package com.smartway.e_canteen.Model;

import java.util.List;

/**
 * Created by Darshan Patel on 10-02-2018.
 */

public class MyResponse {
    public long multicast_id;
    public int success, failure, canonical_ids;
    public List<Result> results;
}
