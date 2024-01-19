package com.example.bmeter.starter;

import java.util.List;

public record GlobalPingResponse(String status, String message, List<ExternalPingResponse> srvPingResponses) {
}
