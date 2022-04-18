package com.wim.aero.acs.model.scp.reply;

public enum enSCPCommErr {

    enSCPCommNoError,				//  0 - no error condition
    // the following errors are cause for terminating comm (after retries)
    enSCPCommTimeout,				//  1 - timeout
    enSCPCommReplyTooLong,			//  2 - invalid reply packet - too long
    enSCPCommCommandTooLong,		//  3 - invalid command packet - too long (rejected by the SCP)
    enSCPCommCRCError,				//  4 - invalid checksum - either SCP rejected the command or bad reply
    enSCPCommSequenceNumber,		//  5 - the reply did not match the transmitted sequence number - out of sync
    enSCPCommDetached,				//  6 - the SCP was detached
    enSCPCommDeleted,				//  7 - the SCP was deleted
    enSCPCommCipher,				//  8 - decryption error of the reply packet

    // connection progress status - technically not errors,
    enSCPCommLogOn,				// 20 - Connection sequence in progress, started log-on (and Aes sync.)
    enSCPCommNoErrAes,				// 21 - Connection OK, AES encryption enabled (same as enSCPCommNoError, with AES)
    enSCPCommTlsHandshake,			// 22 - TLS handshake in-progress. (Might not see this, except on channels being "promoted" having started as cleartext.)
    enSCPCommNoErrTls,				// 23 - Connection OK, TLS encryption enabled.

    // connecton related errors
    enSCPCommScpType,			// 30 - device mismatch - invalid SCP type
    enSCPCommNoPassword,			// 31 - SCP requires password, none was set
    enSCPCommNoAesSet,				// 32 - SCP requires encrypted comm, the dll is not configured for it
    enSCPCommNoAesScp,				// 33 - dll is required to run encrypted comm, the SCP is not configured for it
    enSCPCommAesKey;					// 34 - tried encrypted comm, failed key
}
