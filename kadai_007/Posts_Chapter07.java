package kadai_007;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Posts_Chapter07 {
    
    //データベース情報
    private static final String URL = "jdbc:mysql://localhost:3306/challenge_java";
    private static final String USER = "root";
    private static final String PASSWORD = "sallychill";

    //データベース接続準備
    private static Connection con = null;
    private static PreparedStatement insertStatement = null;
    private static PreparedStatement selectStatement = null;
	
    //データベース接続メソッド
	private static void getConnection() {
     // データベースに接続
        try {
			con = DriverManager.getConnection(
			    URL,
			    USER,
			    PASSWORD
			);
			 System.out.print("データベース接続成功:");
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}       
	}
	
	//SQLクリエ準備メソッド
	private static void createStatement() {
		// SQLクエリを準備
        String insertSql = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES "
        		+ "(1003, '2023-02-08', '昨日の夜は徹夜でした・・', 13),"
        		+ "(1002, '2023-02-08', 'お疲れ様です！', 12),"
        		+ "(1003, '2023-02-09', '今日も頑張ります！', 18),"
        		+ "(1001, '2023-02-09', '無理は禁物ですよ！', 17),"
        		+ "(1002, '2023-02-10', '明日から連休ですね！', 20)";
        String selectSql = "SELECT * FROM posts WHERE user_id = 1002;";
        try {
        	insertStatement = con.prepareStatement(insertSql);
        	selectStatement = con.prepareStatement(selectSql);
        	
        	System.out.println(insertStatement.toString());
        	System.out.println("レコード追加を実行します");
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	//投稿データを追加メソッド
	private static void executeUpdate() {  
            // SQLクエリを実行（DBMSに送信）
        	int rowCnt;
			try {
				rowCnt = insertStatement.executeUpdate();
				System.out.println( rowCnt + "件のレコードが追加されました");
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}   
	}
	
	
	//投稿データを検索メソッド
	private static void executeQuery() {
		try {
            // ユーザーIDが1002の投稿を検索
            ResultSet result = selectStatement.executeQuery();
            System.out.println("ユーザIDが1002のレコードを検索しました");

            // 検索結果を表示
            while (result.next()) {
                Date postedAt = result.getDate("posted_at");
                String postContent = result.getString("post_content");
                int likes = result.getInt("likes");
                System.out.println(result.getRow() + "件目:" + "投稿日時=" + postedAt + "/ 投稿内容=" + postContent + "/ いいね数= " + likes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	//メインメソッド
	public static void main(String[] args) {
			getConnection();	
			createStatement();
			executeUpdate();
			executeQuery();
			// リソースのクローズ
	        try {
	            if (insertStatement != null) {
	                insertStatement.close();
	            }
	            if (selectStatement != null) {
	            	selectStatement.close();
	            }
	            if (con != null) {
	                con.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}


}
