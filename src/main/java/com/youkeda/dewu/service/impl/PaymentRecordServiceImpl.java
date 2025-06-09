package com.youkeda.dewu.service.impl;

import com.youkeda.dewu.dao.PaymentRecordDAO;
import com.youkeda.dewu.dataobject.PaymentRecordDO;
import com.youkeda.dewu.model.PaymentRecord;
import com.youkeda.dewu.param.PaymentRecordQueryParam;
import com.youkeda.dewu.service.PaymentRecordService;
import com.youkeda.dewu.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentRecordServiceImpl implements PaymentRecordService {
    @Autowired
    private PaymentRecordDAO paymentRecordDAO;

    @Override
    public PaymentRecord save(PaymentRecord paymentRecord) {
        if (paymentRecord == null) {
            return null;
        }
        if (StringUtils.isEmpty(paymentRecord.getId())) {
            PaymentRecordDO paymentRecordDO = new PaymentRecordDO(paymentRecord);
            paymentRecordDO.setId(UUIDUtils.getUUID());
            int insertSize = paymentRecordDAO.insert(paymentRecordDO);
            if (insertSize < 1) {
                return null;
            }
            paymentRecord.setId(paymentRecordDO.getId());
            return paymentRecord;
        } else {
            PaymentRecordDO paymentRecordDO = new PaymentRecordDO(paymentRecord);
            int updateSize = paymentRecordDAO.update(paymentRecordDO);
            if (updateSize < 1) {
                return null;
            }
            return paymentRecord;
        }
    }

    @Override
    public List<PaymentRecord> query(PaymentRecordQueryParam queryParam) {
        List<PaymentRecord> paymentRecords = new ArrayList<>();
        List<PaymentRecordDO> paymentRecordDOS = paymentRecordDAO.select(queryParam);
        if (CollectionUtils.isEmpty(paymentRecordDOS)) {
            return paymentRecords;
        }
        paymentRecordDOS.forEach(paymentRecordDO -> {
            PaymentRecord paymentRecord = paymentRecordDO.convertToModel();
            paymentRecords.add(paymentRecord);
        });
        return paymentRecords;
    }

    @Override
    public PaymentRecord updateStatus(PaymentRecord paymentRecord) {
        PaymentRecordDO paymentRecordDO = new PaymentRecordDO(paymentRecord);
        int updateSize = paymentRecordDAO.update(paymentRecordDO);
        if (updateSize < 1) {
            return null;
        }
        return paymentRecord;
    }
}
