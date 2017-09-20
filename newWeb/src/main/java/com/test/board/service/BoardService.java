package com.test.board.service;


import java.util.List;

import com.test.board.Article;
import com.test.board.AttachFile;
import com.test.board.Board;
import com.test.board.Comment;

public interface BoardService {

  //목록
  public List<Article> getArticleList(String boardCd, String searchWord, Integer startRecord, Integer endRecord);

  //총 레코드
  public int getTotalRecord(String boardCd, String searchWord);

  //글쓰기
  public int addArticle(Article article);

  //첨부 파일 추가 
  public void addAttachFile(AttachFile attachFile);

  //글 수정 
  public void modifyArticle(Article article);

  //글 삭제 
  public void removeArticle(int articleNo);

  //조회 수 증가 
  public void increaseHit(int articleNo);

  //상세보기 
  public Article getArticle(int articleNo);

  //다음 글 
  public Article getNextArticle(int articleNo,String boardCd, String searchWord);

  //이전 글 
  public Article getPrevArticle(int articleNo, String boardCd, String searchWord);

  //첨부 파일 리스트 
  public List<AttachFile> getAttachFileList(int articleNo);

  //첨부 파일 삭제 
  public void removeAttachFile(int attachFileNo);

  //게시판 
  public Board getBoard(String boardCd);

  //댓글 쓰기 
  public void addComment(Comment comment);

  //댓글 수정 
  public void modifyComment(Comment comment);

  //댓글 삭제 
  public void removeComment(int commentNo);

  //댓글 리스트 
  public List<Comment> getCommentList(int articleNo);

  //첨부 파일 찾기 
  public AttachFile getAttachFile(String attachFileNm);
  
  //댓글 찾기 
  public Comment getComment(int commentNo);

  //home화면에서 게시글 가져오기 
  public List<Article> getRecentArticle(String boardCd);

}