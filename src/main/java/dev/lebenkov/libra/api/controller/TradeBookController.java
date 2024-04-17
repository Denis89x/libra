package dev.lebenkov.libra.api.controller;

import dev.lebenkov.libra.api.service.TradeService;
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

    TradeService tradeService;

    @PostMapping("/new-trade")
    public ResponseEntity<String> newTrade(@RequestBody TradeRequest tradeRequest) {
        tradeService.sendTradeRequest(tradeRequest);
        return ResponseEntity.ok("Trade was successfully added to the tradebook");
    }

    @PostMapping("/process-trade")
    public ResponseEntity<String> processTrade(@RequestBody TradeProcess tradeProcess) {
        tradeService.processTradeRequest(tradeProcess);
        return ResponseEntity.ok("Trade was successfully processed the trade");
    }

    @GetMapping
    public ResponseEntity<List<TradeResponse>> getTrades() {
        return new ResponseEntity<>(tradeService.getAllTrades(), HttpStatus.OK);
    }
}