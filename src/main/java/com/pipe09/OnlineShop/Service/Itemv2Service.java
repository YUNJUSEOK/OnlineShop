package com.pipe09.OnlineShop.Service;

import com.pipe09.OnlineShop.Domain.Item.V1.Item;
import com.pipe09.OnlineShop.Domain.Item.V2.DTYPE.Itemv2;
import com.pipe09.OnlineShop.Dto.Item.ImgPathDto;
import com.pipe09.OnlineShop.Repository.ItemRepository;
import com.pipe09.OnlineShop.Repository.ItemV2Repository;
import com.pipe09.OnlineShop.Utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class Itemv2Service {
    private final ItemV2Repository itemRepository;

    @Transactional
    public Long save(Itemv2 item){return itemRepository.save(item); }

    public List<Itemv2> findAll(int offset, int limit){ return itemRepository.findAll(offset,limit); }

    public Itemv2 findOne(Long id){
        return itemRepository.findItem(id);
    }

    public Integer getcountofKeyword(String keyword){ return itemRepository.getCountofKeyword(keyword); }

    @Transactional
    public Long updateItem(Itemv2 item){
        Itemv2 changed= itemRepository.findItem(item.getItem_ID());
        changed.setName(item.getName());
        changed.setPrice(item.getPrice());
        changed.setStockQuantity(item.getStockQuantity());
        changed.setDescription(item.getDescription());
        changed.setWeight(item.getWeight());
        changed.setMadeIn(item.getMadeIn());
        changed.setManufacturedCompany(item.getManufacturedCompany());
        if(item.getImgSrc() != null){
            changed.setImgSrc(item.getImgSrc());
        }
        return changed.getItem_ID();
    }
    public List<Itemv2> findAllbyType(String type,int offset,int limit){
        return itemRepository.findAllbyType(type,offset,limit);
    }
    public List<Itemv2> findAllaboutTools(){
        return itemRepository.findAllaboutTools();
    }
    @Transactional
    public boolean removeById(Long id){
        return itemRepository.removeById(id);
    }

    public List<Itemv2> findByTitleKeyword(String keyWord,int offset,int limit){
        return itemRepository.findBytitleKeyword(keyWord, offset, limit);
    }
    public ImgPathDto MakingImgfile(MultipartFile mfile){
        ImgPathDto dto= new ImgPathDto(null,null);
        String str= Utils.deleteKorean(mfile.getOriginalFilename());
        if(mfile!=null) {

            File file=new File(Utils.getImgPATHwithOS()+File.separator+"upload"+File.separator+str);

            try {
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(mfile.getBytes());
                fos.close();
                dto.setName(file.getName());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                dto.setMsg("이미지를 찾을 수 없습니다.");
            } catch (IOException e) {
                e.printStackTrace();
                dto.setMsg("이미지 변환간 에러가 발생했습니다.");
            }
        }
        return dto;

    }
    /*
    public File getImgName(String str,int count){
        File file = new File(Utils.getImgPATHwithOS()+File.separator+"upload"+File.separator+str);
        if(file.exists()){
            return getImgName(str,++count);
        }else{
            return file;
        }


    }

     */

}

