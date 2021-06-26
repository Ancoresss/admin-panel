package com.adpanel.adpanel;

import com.adpanel.adpanel.logic.LinkGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdpanelApplicationTests {

	@Test
	void first5Chars() {
		LinkGenerator lg = new LinkGenerator();
		System.out.println(lg.generateLink());
	}

}
