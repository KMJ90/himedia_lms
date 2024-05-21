package com.ohgiraffers.lmsjpa.common;
import org.springframework.data.domain.Page;

public class Pagenation {
    public static PagingButton getPagingButtonInfo(Page page) {

        // page.getNumber() : Page 객체에서 현재 페이지의 인덱스[0]를 가져옴
        int currentPage = page.getNumber() + 1;  // 페이지 번호는 보통 1부터 시작하므로 + 1

        int defaultButtonCount = 10;  // 페이지에 보여질 버튼의 개수

        // double 타입으로 오름차순정렬 하고, 현재 페이지를 기본 버튼 개수로 나눔
        int startPage = (int) (Math.ceil((double) currentPage / defaultButtonCount) - 1) // - 1 : 인덱스에서 0부터 시작하도록
                        * defaultButtonCount + 1; // + 1 : 시작 페이지의 인덱스를 실제 페이지 번호로 변환

        // 시작 페이지가 1이라면 + 기본 버튼 개수 5 = 6 , - 1을 하여 끝페이지 번호를 설정
        int endPage = startPage + defaultButtonCount - 1;

        // 끝 페이지 번호가 총 페이지 수를 초과하는지 확인, 초과하는 경우 그 값을 총 페이지 수로 설정
        if (page.getTotalPages() < endPage) endPage = page.getTotalPages();

        // page.getTotalPages() == 0 : 총 페이지 수가 0인지 확인 / endPage == 0 : 마지막 페이지가 0인지 확인
        // endPage = startPage : 끝 페이지가 시작 페이지가 되도록 설정
        if (page.getTotalPages() == 0 && endPage == 0) endPage = startPage;

        // (현재 페이지, 시작 페이지, 끝 페이지)를 포함하는 PagingButton 객체를 생성하여 반환
        return new PagingButton(currentPage, startPage, endPage);
    }
}
