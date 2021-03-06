package com.javalearning.restful.restful;


import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;
import java.util.List;


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

    @DeleteMapping("/{id}")
    // curl -X DELETE http://localhost:8080/tvseries/101?delete_reason=duplicated
    public Map<String, String> deleteOne(@PathVariable int id, HttpServletRequest request,
                                         @RequestParam(value = "delete_reason",required = false)String deleteReason) throws Exception
    {
        if(log.isTraceEnabled()){
            log.trace("deleteOne(), id: " + id);
        }
        Map<String,String> result = new HashMap<>();
        if(id == 101){
            //todo: 应该是数据库删除代码
            result.put("message","#101被" + request.getRemoteAddr() + "删除(原因" + deleteReason + ")");
        }else if(id == 102){
            throw new RuntimeException("#102不能被删除");
        }
        else {
            throw new ResourceNotFoundException();
        }

        return  result;
    }


    //上传文件
    //1. 命令行定位到存放poi.jpg文件的目录
    //2. 运行命令： curl -v -F "photo=@poi.jpg" http://localhost:8080/tvseries/102/photos
    //3. poi.jpg会存放到项目的target目录下
    @PostMapping(value = "/{id}/photos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public  void addPhoto(@PathVariable int id, @RequestParam("photo")MultipartFile imgFile) throws Exception{
        if(log.isTraceEnabled()){
            log.trace("接收到文件 " + id + "收到文件: " + imgFile.getOriginalFilename());
        }

        FileOutputStream fos = new FileOutputStream("target/" + imgFile.getOriginalFilename());
        IOUtils.copy(imgFile.getInputStream(),fos);
        fos.close();
    }


    //下载图片
    @GetMapping(value="/{id}/icon",produces = MediaType.IMAGE_JPEG_VALUE)
    //浏览器url：http://localhost:8080/tvseries/101/icon
    public  byte[] getIcon(@PathVariable int id)throws Exception{
        if(log.isTraceEnabled()){
            log.trace("getIcon(" + id +")");
        }
        String iconFile = "src/test/resources/car.jpg";
        InputStream ls = new FileInputStream(iconFile);
        return org.apache.commons.io.IOUtils.toByteArray(ls);
    }



    //验证测试
    //在需要验证的参数前必须加上注解：@Valid  否则验证不会启用
    @PostMapping(value = "/insertOneValidate")
    //验证全部通过：curl -v -H "Content-Type:application/json" -X POST --data '{"id":1,"name":"SuperHero","seasonCount":1,"originRelease":"2016-10-02","tvCharacters":[{"name":"mary"},{"name":"tony"}]}' http://localhost:8080/tvseries/insertOneValidate
    //tvCharacters的数量小于了最小值2: curl -v -H "Content-Type:application/json" -X POST --data '{"id":1,"name":"SuperHero","seasonCount":1,"originRelease":"2016-10-02","tvCharacters":[{"name":"mary"}]}' http://localhost:8080/tvseries/insertOneValidate
    public TvSeriesDto insertOneValidate(@Valid @RequestBody TvSeriesDto tvSeriesDto)
    {
        if(log.isTraceEnabled())
        {
            log.trace("insertOne 传进来的参数是: " + tvSeriesDto);
        }

        //todo: 应该是保存到数据库的代码
        tvSeriesDto.setId(9999);

        return  tvSeriesDto;
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
