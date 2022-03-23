package com.wim.aero.acs.protocol.device.reader;

import lombok.Data;

/**
 * @title: ACRMode
 * @author: Ellie
 * @date: 2022/03/14 09:46
 * @description: 8.8 Command 308: ACR Mode
 *
 * |-------+--------------------------------
 * | Value | Access mode definitions
 * |-------+--------------------------------
 * | 1     | Disable the ACR, no REX
 * | 2     | Unlock (unlimited access)
 * | 3     | Locked (no access, REX active)
 * | 4     | Facility code only
 * | 5     | Card only
 * | 6     | PIN only
 * | 7     | Card and PIN required
 * | 8     | Card or PIN required
 * |-------+--------------------------------
 * | 16    | Disable “2-card” mode (Clear actl_flags - ACR_F_2CARD)
 * | 17    | Enable “2-card” mode (Set actl_flags - ACR_F_2CARD)
 * | 26    | Clear “ACR_FE_NO_ARQ” extended actl_flags
 * | 27    | Set “ACR_FE_NO_ARQ” extended actl_flags
 * | 29    | Start linking mode (Set “ACR_FE_LINK_MODE” extended actl_flags)
 * | 30    | Abort linking mode (Clear “ACR_FE_LINK_MODE” extended actl_flags)
 * | 31    | Extended Feature Change
 * | 32    | Start linking mode for Alternate Reader (Set “ACR_FE_LINK_MODE_ALT” extended actl_flags)
 * | 33    | Abort linking mode for Alternate Reader
 * |-------+-------------------------------------
 **/
@Data
public class ACRMode {
    private int scpNumber;
    private int acrNumber;
    private int acrMode;

    // 预留字段
    private int nAuthModFlags = 0; // Set to 0 - N/A.
    private int nExtFeatureType;  // Used only with acr_mode 31.
    private int iIPB_sio;  // SIO ID for Interior Push Button (not needed for native locksets). Set to -1 when not used.
    private int iIPB_number; // Input number for Interior Push Button (not needed for native locksets)
    private int iIPB_long_press; // IPB long-press, 0-15 seconds (if applicable)
    private int iIPB_out_sio; // SIO ID for IPB indicator output (not needed for native locksets). Set to -1 when not used.
    private int iIPB_out_num;

}
