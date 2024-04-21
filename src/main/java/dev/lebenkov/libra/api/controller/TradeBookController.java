package dev.lebenkov.libra.api.controller;

import dev.lebenkov.libra.api.service.TradeManagementService;
import dev.lebenkov.libra.api.service.TradeReadService;
import dev.lebenkov.libra.storage.dto.TradeProcess;
import dev.lebenkov.libra.storage.dto.TradeRequest;
import dev.lebenkov.libra.storage.dto.TradeResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trades")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TradeBookController {

    TradeReadService tradeReadService;
    TradeManagementService tradeManagementService;

    @PostMapping("/new-trade")
    public ResponseEntity<String> newTrade(@RequestBody TradeRequest tradeRequest) {
        tradeManagementService.sendTradeRequest(tradeRequest);
        return ResponseEntity.ok("Trade was successfully added to the tradebook");
    }

    @PostMapping("/process-trade")
    public ResponseEntity<String> processTrade(@RequestBody TradeProcess tradeProcess) {
        tradeManagementService.processTradeRequest(tradeProcess);
        return ResponseEntity.ok("Trade was successfully processed the trade");
    }

    @GetMapping("/pending-trades")
    public ResponseEntity<List<TradeResponse>> getAllPendingTrades() {
        return new ResponseEntity<>(tradeReadService.getAllPendingTrades(), HttpStatus.OK);
    }

    @GetMapping("/accepted-trades")
    public ResponseEntity<List<TradeResponse>> getAllAcceptedTrades() {
        return new ResponseEntity<>(tradeReadService.getAllAcceptedTrades(), HttpStatus.OK);
    }

    @GetMapping("/rejected-trades")
    public ResponseEntity<List<TradeResponse>> getAllRejectedTrades() {
        return new ResponseEntity<>(tradeReadService.getAllRejectedTrades(), HttpStatus.OK);
    }

    @GetMapping("/{trade_id}")
    public ResponseEntity<TradeResponse> getTrade(@PathVariable("trade_id") Long tradeId) {
        return new ResponseEntity<>(tradeReadService.getTrade(tradeId), HttpStatus.OK);
    }
}