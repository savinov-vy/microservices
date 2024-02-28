package integration.cb.controller;

import integration.cb.service.CbCursService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/money")
@RequiredArgsConstructor
public class ControllerCb {

    private final CbCursService cbCursService;

    @GetMapping("course/{code}")
    public String test(@PathVariable("code") String code) {
        return cbCursService.getCursTodayByCode(code);
    }

}