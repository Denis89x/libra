package dev.lebenkov.libra.api.controller;

import dev.lebenkov.libra.api.service.TradeService;
import dev.lebenkov.libra.storage.dto.TradeProcess;
import dev.lebenkov.libra.storage.dto.TradeRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}