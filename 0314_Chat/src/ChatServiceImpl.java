import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ChatServiceImpl implements ChatService {
	// private List<DataOutputStream> list; // 사용자가 접속했을 때 기억하려고
	private HashMap<String, DataOutputStream> list;

	public ChatServiceImpl() {
		list = new HashMap<>();

	}

	@Override
	// 입장하는 만큼 값을 변경하기 때문에 동기화 문제가 발생할 수 있다
	public synchronized int enter(String nickname, DataOutputStream outputStream) {
		System.out.println(nickname + "사용자가 입장을 요청함");
		int a = list.size();
		int count = 0;
		list.put(nickname, outputStream);

		if (list.size() == a) {
			count = 1;
		}
		return count;

	}

	@Override
	public synchronized void chat(String message) {

		try {
//			for (DataOutputStream dos : list) {
//				dos.writeUTF(message);
//				dos.flush();
//			}
			Set key = list.keySet();
			for (Iterator iterator = key.iterator(); iterator.hasNext();) {
				String keyvalue = (String) iterator.next();
				DataOutputStream value = list.get(keyvalue);
				value.writeUTF(message);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public synchronized void quit(DataOutputStream ouputStream) {
		list.remove(ouputStream);
	}

}
