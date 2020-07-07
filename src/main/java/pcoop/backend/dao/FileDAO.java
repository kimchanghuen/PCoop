package pcoop.backend.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import pcoop.backend.dto.DirectoryDTO;
import pcoop.backend.dto.FileDTO;

@Repository
public class FileDAO {

	@Autowired
	private JdbcTemplate jdbc;

	public int insertRootDirectory(int seq, String name, String path) {
		String sql = "insert into directory values(directory_seq.nextval, ?, null, ?, ?, 'Y')";
		return jdbc.update(sql, seq, name, path);
	}
	
	// 이름으로 디렉토리 seq 검색
	public int getDirSeqByName(String name, int parent_seq) {
		String sql = "select seq from directory where name = ? and parent_seq = ?";
		return jdbc.queryForObject(sql, new Object[] {name, parent_seq}, Integer.class);
	}

	// seq로 디렉토리 경로 검색
	public String getDirPathBySeq(int seq) {

		String sql = "select path from directory where seq = ?";

		return jdbc.queryForObject(sql, new Object[] {seq}, String.class);
	}
	
	// seq로 디렉토리의 parent_seq 검색
	public int getParentSeqBySeq(int seq) {
		String sql = "select parent_seq from directory where seq = ?";
		return jdbc.queryForObject(sql, new Object[] {seq}, Integer.class);
	}
	
	// path로 디렉토리의 seq 검색
	public int getDirSeqByPath(String path) {
		String sql = "select seq from directory where path = ?";
		return jdbc.queryForObject(sql, new Object[] {path}, Integer.class);
	}
	
	// 디렉토리 중복 확인
	public int checkDuplDirName(int parent_seq, String name) {
		String sql = "select count(*) from directory where parent_seq = ? and name = ?";
		return jdbc.queryForObject(sql, new Object[] {parent_seq, name}, Integer.class);
	}

	// 디렉토리 insert
	public int insertDirectory(String path, String name, int parent_seq) {

		String sql = "insert into directory values(DIRECTORY_SEQ.nextval, ?, ?, ?, ?, 'N')";

		return jdbc.update(sql, 11, parent_seq, name, path);
	}

	// 디렉토리 delete
	public int deleteDirectory(String path) {
		String sql = "delete from directory where path like ?";
		return jdbc.update(sql, path + "%");
	}
	
	// 디렉토리 이름 및 path 변경
	public int renameDirectory(int seq, String rename, String repath) {
		String sql = "update directory set name = ?, path = ? where seq = ?";
		return jdbc.update(sql, rename, repath, seq);
	}
	
	public int repathFileByDirSeq(int seq, String repath, String frepath) {
		String sql = "update files set directory_path = ?, path = ? where seq = ?";
		return jdbc.update(sql, repath, frepath, seq);
	}

	// 디렉토리 리스트
	public List<DirectoryDTO> getDirList(){

		String sql = "select * from directory where root_yn = 'N' order by 1";
		return jdbc.query(sql, new RowMapper<DirectoryDTO>() {
			@Override
			public DirectoryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

				DirectoryDTO dto = new DirectoryDTO();
				dto.setSeq(rs.getInt("seq"));
				dto.setProject_seq(rs.getInt("project_seq"));
				dto.setName(rs.getString("name"));
				dto.setPath(rs.getString("path"));
				dto.setRoot_yn(rs.getString("root_yn"));

				return dto;

			}
		});

	}

	// 파일 이름 가져오기
	public String getFileNameBySeq(int seq) {
		String sql = "select name from files where seq = ?";
		return jdbc.queryForObject(sql, new Object[] {seq}, String.class);
	}

	// 파일 경로 가져오기
	public String getFilePathBySeq(int seq) {

		String sql = "select path from files where seq = ?";

		return jdbc.queryForObject(sql, new Object[] {seq}, String.class);
	}

	// 파일의 확장자가 텍스트인지 체크
	public int isTextFile(String extension) {
		String sql = "select count(*) from extension where extension = ?";
		return jdbc.queryForObject(sql, new Object[] {extension}, Integer.class);
	}

	// 파일 확장자 가져오기
	public String getFileExtensionBySeq(int seq) {
		String sql = "select extension from files where seq = ?";
		return jdbc.queryForObject(sql, new Object[] {seq}, String.class);
	}

	// 같은 디렉토리 내 파일명 중복 확인
	public int checkDuplFileName(int directory_seq, String name) {

		String sql = "select count(*) from files where directory_seq = ? and name = ?";
		return jdbc.queryForObject(sql, new Object[] {directory_seq, name}, Integer.class);
	}

	// 특정 디렉토리 내 파일 리스트
	public List<FileDTO> getFileListByDirSeq(int dir_seq){

		String sql = "select * from files where directory_seq = ?";
		return jdbc.query(sql, new Object[] {dir_seq}, new RowMapper<FileDTO>(){
			public FileDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

				FileDTO dto = new FileDTO();

				dto.setSeq(rs.getInt("seq"));
				dto.setProject_seq(rs.getInt("project_seq"));
				dto.setDirectory_seq(rs.getInt("directory_seq"));
				dto.setDirectory_path(rs.getString("directory_path"));
				dto.setName(rs.getString("name"));
				dto.setExtension(rs.getString("extension"));
				dto.setPath(rs.getString("path"));
				dto.setUpload_date(rs.getTimestamp("upload_date"));
				dto.setUploader(rs.getString("uploader"));
				dto.setText_yn((rs.getString("text_yn")));

				return dto;
			};
		});
	}

	// 특정 디렉토리 내 모든 파일 지우기
	public int deleteFileByDir(int dir_seq) {
		String sql = "delete from files where directory_seq = ?";
		return jdbc.update(sql, dir_seq);
	}

	// 전체 파일 리스트
	public List<FileDTO> getFileList(){

		String sql = "select * from files";

		return jdbc.query(sql, new RowMapper<FileDTO>() {
			@Override
			public FileDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

				FileDTO dto = new FileDTO();

				dto.setSeq(rs.getInt("seq"));
				dto.setProject_seq(rs.getInt("project_seq"));
				dto.setDirectory_seq(rs.getInt("directory_seq"));
				dto.setDirectory_path(rs.getString("directory_path"));
				dto.setName(rs.getString("name"));
				dto.setExtension(rs.getString("extension"));
				dto.setPath(rs.getString("path"));
				dto.setUpload_date(rs.getTimestamp("upload_date"));
				dto.setUploader(rs.getString("uploader"));
				dto.setText_yn((rs.getString("text_yn")));

				return dto;
			}
		});

	}

	// 파일 생성
	public int insertFile(int project_seq, int directory_seq, String directory_path,
			String name, String extension, String path, String uploader, String text_yn){

		String sql = "insert into files values(files_seq.nextval, ?, ?, ?, ?, ?, ?, sysdate, ?, ?)";
		return jdbc.update(sql, project_seq, directory_seq, directory_path, name, extension, path, uploader, text_yn);
	}

	// 파일 삭제
	public int deleteFile(int seq) {
		String sql = "delete from files where seq = ?";
		return jdbc.update(sql, seq);
	}
	
	public int renameFile(int seq, String rename, String repath) {
		String sql = "update files set name = ?, path = ? where seq = ?";
		return jdbc.update(sql, rename, repath, seq);
	}

	// DB 'extension' 테이블의 데이터들 저장용 - 임시 함수
	//	public int insertExtensions(String extension) {
	//		String sql = "insert into extension values(extension_seq.nextval, ?)";
	//		return jdbc.update(sql, extension);
	//	}

}
