package com.br.salespda.common;

import com.br.salespda.basic.Packing.FirstCheckBillResult;

import hardware.print.printer;
import hardware.print.printer.PrintType;
import android.graphics.Paint;

public class PrinterService {

	/**
	 * ��ӡ��װ����㵥-���쵥
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
					m_printer.PrintStringEx("\n��װ����㵥\n", 40, false, true,
							printer.PrintType.Centering);
					String str = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
					// ����
					m_printer.PrintLineInit(18);
					m_printer.PrintLineString(billInfo.ReceivingBillNO, 18,
							PrintType.Left, true);
					// �Ƶ�ʱ��
					m_printer.PrintLineString(billInfo.MakeDate, 18, 210, true);
					m_printer.PrintLineEnd();

					// �ͻ�����
					m_printer.PrintLineInit(18);
					m_printer.PrintLineString("(" + billInfo.CustomerCode + ")"
							+ billInfo.CustomerName, 18, PrintType.Left, true);
					m_printer.PrintLineEnd();

					// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					// ��װ������
					m_printer.PrintLineInit(18);
					m_printer.PrintLineString("(" + billInfo.PackingCode + ")"
							+ billInfo.PackingName, 18, PrintType.Left, true);
					m_printer.PrintLineEnd();

					// ��λ
					// ��������
					// ��Ѻ����
					// ��Ѻ���
					// �˽�����
					// ����������
					// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					// ���ϼ�
					m_printer.PrintLineInit(18);
					m_printer.PrintLineString("���ϼ�" + billInfo.SumClaimMoney,
							18, PrintType.Right, true);
					m_printer.PrintLineEnd();
					// �˻�������Ϣ

					// ���ճ���
					m_printer.PrintLineInit(18);
					m_printer.PrintLineString("���ճ���" + billInfo.StorehouseName,
							18, PrintType.Left, true);
					m_printer.PrintLineEnd();
					// ҵ������
					// ���䷽ʽ
					// �볧����
					m_printer.PrintLineInit(18);
					m_printer.PrintLineString("�볧����" + billInfo.TruckNO, 18,
							PrintType.Left, true);
					m_printer.PrintLineEnd();

					// ��ע

					// ������
					m_printer.PrintLineInit(18);
					m_printer.PrintLineString("������" + billInfo.FirstCheckUser,
							18, PrintType.Left, true);
					m_printer.PrintLineEnd();
					// ������
					// �Ƶ���
					// �ͻ�
				}
			}
		} catch (Exception ex) {
			
		}

	}
}
