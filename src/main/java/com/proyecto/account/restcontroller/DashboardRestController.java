package com.proyecto.account.restcontroller;


import com.proyecto.account.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardRestController {
    @Autowired
    private IUserRepository userRepository;
    @GetMapping("/statistics")
    public HashMap<String, Object> getDashboardStatistics(){
        //long lProductCount = productRepository.count();
        //long lProductTypeCount = productTypeRepository.count();
        long lUserCount = userRepository.count();
        long lVisitorsCount = 0;

        HashMap<String, Object> map = new HashMap<>();
        //map.put("count_product", lProductCount);
        //map.put("count_producttype", lProductTypeCount);
        map.put("count_user", lUserCount);
        map.put("count_visitors", lVisitorsCount);

        //Area Chart
        List<MorisArea> morisAreas = new ArrayList<>();
        morisAreas.add(new MorisArea("2011 Q1", 2666, 2666 ));
        morisAreas.add(new MorisArea("2011 Q2", 2778, 2294 ));
        morisAreas.add(new MorisArea("2011 Q3", 4912, 1969 ));
        morisAreas.add(new MorisArea("2011 Q4", 3767, 3597 ));

        morisAreas.add(new MorisArea("2012 Q1", 6810, 1914 ));
        morisAreas.add(new MorisArea("2012 Q2", 5670, 4293 ));
        morisAreas.add(new MorisArea("2012 Q3", 4820, 3795 ));
        morisAreas.add(new MorisArea("2012 Q4", 15073, 5967 ));

        morisAreas.add(new MorisArea("2013 Q1", 10687, 4460 ));
        morisAreas.add(new MorisArea("2013 Q2", 8432, 5713 ));

        map.put("areachart", morisAreas);

        //Line Chart
        List<MorisLine> morisLine = new ArrayList<>();
        morisLine.add(new MorisLine("2011 Q1", 2666 ));
        morisLine.add(new MorisLine("2011 Q2", 2778 ));
        morisLine.add(new MorisLine("2011 Q3", 4912 ));
        morisLine.add(new MorisLine("2011 Q4", 3767 ));
        morisLine.add(new MorisLine("2012 Q1", 6810 ));
        morisLine.add(new MorisLine("2012 Q2", 5670 ));
        morisLine.add(new MorisLine("2012 Q3", 4820 ));
        morisLine.add(new MorisLine("2012 Q4", 15073 ));
        morisLine.add(new MorisLine("2013 Q1", 10687 ));
        morisLine.add(new MorisLine("2013 Q2", 8432 ));

        map.put("linechart", morisLine);

        //Donut Chart
        List<MorisDonut> morisDonuts = new ArrayList<>();
        morisDonuts.add(new MorisDonut("Download Sales", 12 ));
        morisDonuts.add(new MorisDonut("In-Store Sales", 30 ));
        morisDonuts.add(new MorisDonut("Mail-Order Sales", 20 ));

        map.put("donutchart", morisDonuts);

        return map;
    }
}
