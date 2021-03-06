package config;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class pageResource {
    @RequestMapping(value = "/expo")
    public String expo() {
        return "expo";
    }
    @RequestMapping(value = "/qrcode/{start_sku}/{end_sku}/{category}")
    public String test(Model m, @PathVariable int start_sku, @PathVariable int end_sku, @PathVariable String category) {
        final int width=3;


        List<List> skus=new ArrayList<List>();
        if(start_sku>end_sku) {
            m.addAttribute("error", "Invalid Parameters: start sku > end sku!");
            return "test";
        }


        int sku=start_sku;
        List<Integer> line=new ArrayList<Integer>();
        do{
            if(line.size()==width){
                skus.add(line);
                line=new ArrayList<Integer>();
            }

            line.add(sku);

            sku++;
        }while(sku<=end_sku);

        if(line.size()>0)
            skus.add(line);

        m.addAttribute("skus",skus);
        m.addAttribute("category",category);
        return "test";
    }

}
