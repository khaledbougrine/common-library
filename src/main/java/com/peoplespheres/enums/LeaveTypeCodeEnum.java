package com.peoplespheres.enums;

// Jackson imports

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

/** Enumeration to be used for the code about the type of leave considered.
 * CP -> Leave request associated is of type CP
 * RTT -> Leave request associated is of type RTT
 */
public enum LeaveTypeCodeEnum {
    @JsonProperty("CP")
    CP("CP"),
    @JsonProperty("RTT")
    RTT("RTT");

    /** The direction of the flow associated to this enumeration */
    public final String leaveTypeCode;

    /** Default constructor of this enumeration
     *
     * @param leaveTypeCode The standard code for the considered type of leave to be assigned to this enumeration.
     */
    LeaveTypeCodeEnum(@NonNull final String leaveTypeCode) {
        this.leaveTypeCode = leaveTypeCode;
    }
}
