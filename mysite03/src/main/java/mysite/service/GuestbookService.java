package mysite.service;

import java.util.List;

import org.springframework.stereotype.Service;

import mysite.repository.GuestbookRepository;
import mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	private GuestbookRepository guestbookRepository;
	
	public GuestbookService() {
		this.guestbookRepository=guestbookRepository;
	}
	
	public List<GuestbookVo> getContentsList(){
		return guestbookRepository.findAll();
	}
	
	public void deleteContents(Long id, String password) {
		
	}
	
	public void addContents(GuestbookVo vo) {
		
	}
}
