package kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Scores_Chapter10 {
	
	//データベース情報
    private static final String URL = "jdbc:mysql://localhost:3306/challenge_java";
    private static final String USER = "root";
    private static final String PASSWORD = "sallychill";
    
    //データベース接続準備
    private static Connection con = null;
    private static PreparedStatement updateStatement = null;
    private static PreparedStatement orderStatement = null;
	
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
		String updateSql = "UPDATE scores SET score_math = '95', score_english = '80' WHERE id = 5";
		String orderSql = "SELECT id, name, score_math, score_english FROM scores ORDER BY score_math DESC, score_english DESC;";
		 try {
	        	updateStatement = con.prepareStatement(updateSql);
	        	orderStatement = con.prepareStatement(orderSql);
	        	
	        	System.out.println(updateStatement.toString());
	        	System.out.println("レコード更新を実行します");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}
	
	//点数データ更新メソッド
	private static void executeUpdate() {
		 // SQLクエリを実行（DBMSに送信）
    	int rowCnt;
		try {
			rowCnt = updateStatement.executeUpdate();
			System.out.println(rowCnt + "件のレコードが追加されました");
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}   
	}
	
	//点数順に並ぶメソッド
	private static void executeQuery() {
		// SQLクエリを実行（DBMSに送信）
		try {
            ResultSet result = orderStatement.executeQuery();
            System.out.println("数学・英語の点数が高い順に並べ替えました");

            // 検索結果を表示
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                int score_math = result.getInt("score_math");
                int score_english = result.getInt("score_english");
                
                System.out.println(result.getRow() + "件目:" + "生徒ID=" + id + "/ 氏名=" 
                                  + name + "/ 数学= " + score_math + "/ 英語=" + score_english);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	//メインメソッド
	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		getConnection();	
		createStatement();
		executeUpdate();
		executeQuery();
		// リソースのクローズ
        try {
            if (updateStatement != null) {
                updateStatement.close();
            }
            if (orderStatement != null) {
            	orderStatement.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

}
