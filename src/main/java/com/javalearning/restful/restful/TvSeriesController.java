package com.javalearning.restful.restful;

import jdk.management.resource.ResourceRequestDeniedException;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/tvseries")
public class TvSeriesController {

    private  static  final Log log = LogFactory.getLog(TvSeriesController.class);

//    @GetMapping
//    public Map<String,Object> sayHello()
//    {
//        Map<String,Object> result = new HashMap<>();
//        result.put("message","hello world");
//
//        return result;
//    }

    @GetMapping
    public List<TvSeriesDto> getAll()
    {
        if(log.isTraceEnabled())
        {
            log.trace("getALL()开始运行了");
        }

        List<TvSeriesDto> list = new ArrayList<>();
        //日期型转JSON格式 如果没有进设置的话，日期会显示成：2016-10-01T16:00:48.010+0000
        //属性设置：在TvSeriesDto中的originRelease上使用@JsonFormat注解
        //全局设置：在application.yml中设置（把application.properities重命名为application.yml。其实都是配置文件，就是里面的格式和规则不一样）
        list.add(createWestWorld());
        list.add(createPoi());
        return  list;
    }


    @GetMapping("/{id}")
    public TvSeriesDto getOne(@PathVariable int id)
    {
       if(log.isTraceEnabled())
       {
           log.trace("getOne(): id " + id);
       }

       if(id == 101)
       {
           return createWestWorld();
       }else if(id == 102)
       {
           return  createPoi();
       }
       else{
           throw new ResourceNotFoundException();
       }

    }

    @PostMapping
    //curl发送请求：curl -H "Content-Type:application/json" -X POST --data '{"id":1,"name":"SuperHero","seasonCount":1,"originRelease":"2016-10-02"}' http://localhost:8080/tvseries
    public TvSeriesDto insertOne(@RequestBody TvSeriesDto tvSeriesDto)
    {
        if(log.isTraceEnabled())
        {
            log.trace("insertOne 传进来的参数是: " + tvSeriesDto);
        }

        //todo: 应该是保存到数据库的代码
        tvSeriesDto.setId(9999);

        return  tvSeriesDto;
    }


    @PutMapping("/{id}")
    //curl发送请求： curl -H "Content-Type:application/json" -X PUT --data '{"id":1,"name":"aaa","seasonCount":1,"originRelease":"2016-10-02"}' http://localhost:8080/tvseries/101
    public  TvSeriesDto updateOne(@PathVariable int id, @RequestBody TvSeriesDto tvSeriesDto){
        if(log.isTraceEnabled()){
            log.trace("updateOne(), id: " +  id );
        }

        if(id == 101){
            //todo: 应该是数据库更新的代码
            return  createWestWorld();
        }else{
            throw new ResourceNotFoundException();
        }



    }



    private TvSeriesDto createWestWorld()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016,Calendar.OCTOBER,2,0,0);
        return  new TvSeriesDto(1,"WestWorld",1,calendar.getTime());

    }

    private  TvSeriesDto createPoi()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2011,Calendar.SEPTEMBER,2,0,0);
        return new TvSeriesDto(1,"Persion of Interests",5,calendar.getTime());
    }

}
