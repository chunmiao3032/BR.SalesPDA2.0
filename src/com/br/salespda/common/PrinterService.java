package com.br.salespda.common;

import com.br.salespda.basic.Packing.FirstCheckBillResult;

import hardware.print.printer;
import hardware.print.printer.PrintType;
import android.graphics.Paint;

public class PrinterService {

	/**
	 * 打印包装物结算单-复检单
	 */
	public static void PrintSecondCheckBill(FirstCheckBillResult billInfo) {
		try {
			printer m_printer = new printer();
			Paint m_pat = new Paint();

			m_printer.Open();

			boolean flag1 = m_printer.IsOutOfPaper();
			// TODO Auto-generated method stub
			if ((!m_printer.IsOverTemperature() && !flag1)) {
				int i = 0;
				for (i = 0; i < 1; i++) {
					m_printer.PrintStringEx("\n包装物结算单\n", 40, false, true,
							printer.PrintType.Centering);
					String str = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
					// 单号
					m_printer.PrintLineInit(18);
					m_printer.PrintLineString(billInfo.ReceivingBillNO, 18,
							PrintType.Left, true);
					// 制单时间
					m_printer.PrintLineString(billInfo.MakeDate, 18, 210, true);
					m_printer.PrintLineEnd();

					// 客户名称
					m_printer.PrintLineInit(18);
					m_printer.PrintLineString("(" + billInfo.CustomerCode + ")"
							+ billInfo.CustomerName, 18, PrintType.Left, true);
					m_printer.PrintLineEnd();

					// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					// 包装物名称
					m_printer.PrintLineInit(18);
					m_printer.PrintLineString("(" + billInfo.PackingCode + ")"
							+ billInfo.PackingName, 18, PrintType.Left, true);
					m_printer.PrintLineEnd();

					// 单位
					// 验收数量
					// 退押数量
					// 退押金额
					// 退借数量
					// 待保管数量
					// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					// 金额合计
					m_printer.PrintLineInit(18);
					m_printer.PrintLineString("金额合计" + billInfo.SumClaimMoney,
							18, PrintType.Right, true);
					m_printer.PrintLineEnd();
					// 账户结余信息

					// 验收场地
					m_printer.PrintLineInit(18);
					m_printer.PrintLineString("验收场地" + billInfo.StorehouseName,
							18, PrintType.Left, true);
					m_printer.PrintLineEnd();
					// 业务类型
					// 运输方式
					// 入厂车号
					m_printer.PrintLineInit(18);
					m_printer.PrintLineString("入厂车号" + billInfo.TruckNO, 18,
							PrintType.Left, true);
					m_printer.PrintLineEnd();

					// 备注

					// 初检人
					m_printer.PrintLineInit(18);
					m_printer.PrintLineString("初检人" + billInfo.FirstCheckUser,
							18, PrintType.Left, true);
					m_printer.PrintLineEnd();
					// 复检人
					// 制单人
					// 客户
				}
			}
		} catch (Exception ex) {
			
		}

	}
}
