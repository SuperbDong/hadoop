/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.mapreduce.jobhistory;

import org.apache.avro.util.Utf8;
import org.apache.hadoop.mapreduce.JobID;
import org.apache.hadoop.util.StringUtils;
import org.apache.hadoop.yarn.api.records.timelineservice.TimelineEvent;

@SuppressWarnings("deprecation")
public class JobQueueChangeEvent implements HistoryEvent {
  private JobQueueChange datum = new JobQueueChange();
  
  public JobQueueChangeEvent(JobID id, String queueName) {
    datum.jobid = new Utf8(id.toString());
    datum.jobQueueName = new Utf8(queueName);
  }
  
  JobQueueChangeEvent() { }
  
  @Override
  public EventType getEventType() {
    return EventType.JOB_QUEUE_CHANGED;
  }

  @Override
  public Object getDatum() {
    return datum;
  }

  @Override
  public void setDatum(Object datum) {
    this.datum = (JobQueueChange) datum;
  }
  
  /** Get the Job ID */
  public JobID getJobId() {
    return JobID.forName(datum.jobid.toString());
  }
  
  /** Get the new Job queue name */
  public String getJobQueueName() {
    if (datum.jobQueueName != null) {
      return datum.jobQueueName.toString();
    }
    return null;
  }
  
  @Override
  public TimelineEvent toTimelineEvent() {
    TimelineEvent tEvent = new TimelineEvent();
    tEvent.setId(StringUtils.toUpperCase(getEventType().name()));
    tEvent.addInfo("QUEUE_NAMES", getJobQueueName());
    return tEvent;
  }

}
